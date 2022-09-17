package com.in28minutes.springboot.learnjpaandhibernate.course.springdatajpa;

import com.in28minutes.springboot.learnjpaandhibernate.course.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseSpringDataJpaRepositoryTest {

    @Autowired
    CourseSpringDataJpaRepository courseSpringDataJpaRepository;

    Course course = new Course(100L,"Intro to Gradle", "in28minutes");

    @BeforeEach
    public void setUp(){
        courseSpringDataJpaRepository.save(course);
    }

    @Test
    public void findByNameShouldSucceed(){
       List<Course> courses = courseSpringDataJpaRepository.findByName("Intro to Gradle");
       Course course = courses.get(0);
       assertTrue(course.getName().equals("Intro to Gradle"));
       assertTrue(course.getAuthor().equals("in28minutes"));
       assertTrue(course.getId()==100L);
    }


    @Test
    public void findByAuthorShouldSucceed(){
       List<Course> courses = courseSpringDataJpaRepository.findByAuthor("in28minutes");
        Course course = courses.get(0);
        assertTrue(course.getName().equals("Intro to Gradle"));
        assertTrue(course.getAuthor().equals("in28minutes"));
        assertTrue(course.getId()==100L);
    }

    @Test
    public void countShouldSucceed(){
       long courseCount =  courseSpringDataJpaRepository.count();
       assertTrue(courseCount==1);
    }

    @Test
    public void saveShouldSucceed(){
        Course course = new Course(200L, "Intro to Maven", "in28minutes");
        Course savedCourse =  courseSpringDataJpaRepository.save(course);
        assertTrue(savedCourse.getName().equals("Intro to Maven"));
        List<Course> courses = courseSpringDataJpaRepository.findAll();
        assertTrue(courses.size()==2);
    }


    @Test
    public void saveAllShouldSucceed(){
        Course course1 = new Course(300L, "Intro to H2", "in28minutes");
        Course course2 = new Course(400L, "Intro to Kafka", "in28mintues");
        List newCourses = List.of(course1, course2);
        Iterable<Course> savedCourses =
                 courseSpringDataJpaRepository.saveAll(newCourses);
        AtomicInteger count = new AtomicInteger();
        savedCourses.forEach(course -> { count.getAndIncrement();
                                          assertTrue(course.getName()!=null);
                                        });
        assertTrue(count.get()==2);
    }

    @Test
    public void deleteShouldSucceed(){
        courseSpringDataJpaRepository.deleteById(100L);
        assertTrue(courseSpringDataJpaRepository.count()==0);
    }
}