package com.in28minutes.springboot.learnjpaandhibernate.course.jdbc;

import com.in28minutes.springboot.learnjpaandhibernate.course.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseJdbcRepositoryTest {

    @Mock
    JdbcTemplate  springJdbcTemplate;

    @InjectMocks
    CourseJdbcRepository courseJdbcRepository;

    @Test
    public void insertShouldSucceed(){
        Course course = new Course();
        course.setId(100);
        course.setName("Spring Boot 3");
        course.setAuthor("in28minutes");

        when(springJdbcTemplate.update(anyString(),anyLong(), anyString(),anyString())).thenReturn(1);
        courseJdbcRepository.insert(course);
        verify(springJdbcTemplate,times(1)).update(anyString(),any(),any(),any());
    }

    @Test
    public void deleteShouldSucceed(){

        when(springJdbcTemplate.update(anyString(), anyLong())).thenReturn(1);
        courseJdbcRepository.deleteById(1L);
        verify(springJdbcTemplate,times(1)).update(anyString(),anyLong());
    }

    @Test
    public void findShouldSucceed(){

        Course jscourse = new Course();
        jscourse.setId(10L);
        jscourse.setName("Intro to Javascript");
         when(springJdbcTemplate.queryForObject(anyString(),
                any(BeanPropertyRowMapper.class), anyLong())).thenReturn(jscourse);

       assertTrue(courseJdbcRepository.findById(10L).getName().equals("Intro to Javascript"));
       verify(springJdbcTemplate,times(1))
               .queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyLong());

    }
}