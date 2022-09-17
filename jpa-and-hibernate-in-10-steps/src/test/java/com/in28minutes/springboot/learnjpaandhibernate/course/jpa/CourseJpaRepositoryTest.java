package com.in28minutes.springboot.learnjpaandhibernate.course.jpa;

import com.in28minutes.springboot.learnjpaandhibernate.LearnJpaAndHibernateApplication;
import com.in28minutes.springboot.learnjpaandhibernate.course.Course;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseJpaRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Sql({"/test-data.sql"})
    public void findByIdShouldSucceed(){
       Course course = entityManager.find(Course.class, 1L);
       assertTrue(course.getName().equals("Intro to AWS"));
    }

    @Test
    @Transactional
    public void mergeShouldSucceed(){
        Course course = new Course();
        course.setId(2L);
        course.setName("Geography of Asia");
        course.setAuthor("John Smith");
        Course insertedCourse = entityManager.merge(course);
        assertTrue(insertedCourse.getName().equals("Geography of Asia"));
    }


    @Test
    @Transactional
    public void deleteShouldSucceed(){
        Course course = new Course();
        course.setId(100L);
        course.setName("History of Asia");
        course.setAuthor("Kevin Smith");
        entityManager.merge(course);
        Course course1 = entityManager.find(Course.class, 100L);
        entityManager.remove(course1);
        Course notExistCourse = entityManager.find(Course.class, 100L);
        assertTrue(notExistCourse == null);
    }
}