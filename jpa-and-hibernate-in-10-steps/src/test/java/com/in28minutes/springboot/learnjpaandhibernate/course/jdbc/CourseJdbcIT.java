package com.in28minutes.springboot.learnjpaandhibernate.course.jdbc;

import com.in28minutes.springboot.learnjpaandhibernate.course.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@ComponentScan
@Sql({"/test-data.sql"})
public class CourseJdbcIT {

    @Autowired
    CourseJdbcRepository courseJdbcRepository;

    @Test
    public void insertShouldSucceed(){

        Course course = new Course();
        course.setId(5);
        course.setName("Spring Framework 6");
        course.setAuthor("in28minutes");
        courseJdbcRepository.insert(course);
        Course courseFound = courseJdbcRepository.findById(5);
        assertTrue(courseFound.getId()==5);
    }


    @Test
    public void deleteShouldSucceed(){
       Course found= courseJdbcRepository.findById(1L);
       assertTrue(found.getName().equals("Intro to AWS"));
       courseJdbcRepository.deleteById(1L);
       assertThrows(EmptyResultDataAccessException.class,
               ()->courseJdbcRepository.findById(1L));

    }
}
