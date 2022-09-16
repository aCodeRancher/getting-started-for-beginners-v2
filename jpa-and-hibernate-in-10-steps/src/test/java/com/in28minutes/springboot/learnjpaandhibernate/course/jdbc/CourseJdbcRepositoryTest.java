package com.in28minutes.springboot.learnjpaandhibernate.course.jdbc;

import com.in28minutes.springboot.learnjpaandhibernate.course.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CourseJdbcRepositoryTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    CourseJdbcRepository courseJdbcRepository;

    @Test
    public void insertShouldSucceed(){
        Course course = new Course();
        course.setId(100);
        course.setName("Spring Boot 3");
        course.setAuthor("in28minutes");
        String INSERT_QUERY =

                """
                    insert into course (id, name, author)
                    values(?, ?,?);
        
                """;
        when(jdbcTemplate.update(INSERT_QUERY,course.getId(), course.getName(), course.getAuthor())).thenReturn(1);
        courseJdbcRepository.insert(course);
        verify(jdbcTemplate,times(1)).update(anyString(),any(),any(),any());
    }

    @Test
    public void deleteShouldSucceed(){
        String DELETE_QUERY =

                """
                    delete from course
                    where id = ?
        
                """;
        when(jdbcTemplate.update(eq(DELETE_QUERY), anyLong())).thenReturn(1);
        courseJdbcRepository.deleteById(1L);
        verify(jdbcTemplate,times(1)).update(anyString(),anyLong());
    }

}