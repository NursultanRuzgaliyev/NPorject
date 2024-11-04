package kz.ruzgaliyev.Project.controllertest;

import kz.ruzgaliyev.Project.controllers.CourseController;
import kz.ruzgaliyev.Project.dtos.CourseDTO;
import kz.ruzgaliyev.Project.entities.Course;
import kz.ruzgaliyev.Project.exception.ResourceNotFoundException;
import kz.ruzgaliyev.Project.repositories.CourseRepository;
import kz.ruzgaliyev.Project.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseControllerTest {


    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;
    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCourses() {
        // Mock data
        CourseDTO course1 = new CourseDTO();
        course1.setId(1L);
        course1.setTitle("Course 1");

        CourseDTO course2 = new CourseDTO();
        course2.setId(2L);
        course2.setTitle("Course 2");

        when(courseService.getAllCourses()).thenReturn(Arrays.asList(course1, course2));

        // Call method
        ResponseEntity<List<CourseDTO>> response = courseController.getAllCourses();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetCourseById_Found() {
        // Mock data
        CourseDTO course = new CourseDTO();
        course.setId(1L);
        course.setTitle("Course 1");

        when(courseService.getCourseById(1L)).thenReturn(course);

        // Call method
        ResponseEntity<CourseDTO> response = courseController.getCourseById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Course 1", response.getBody().getTitle());
    }

    @Test
    void testGetCourseById_NotFound() {
        when(courseService.getCourseById(1L)).thenReturn(null);

        // Call method
        ResponseEntity<CourseDTO> response = courseController.getCourseById(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateCourse() {
        // Mock data
        CourseDTO course = new CourseDTO();
        course.setTitle("New Course");

        doNothing().when(courseService).createCourse(course);

        // Call method
        ResponseEntity<Void> response = courseController.createCourse(course);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testUpdateCourse() {
        // Mock data
        CourseDTO course = new CourseDTO();
        course.setId(1L);
        course.setTitle("Updated Course");

        doNothing().when(courseService).updateCourse(course);

        // Call method
        ResponseEntity<Void> response = courseController.updateCourse(course);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteCourse_Found() {
        Long courseId = 1L;

        // Настраиваем мок, чтобы существовал курс
        when(courseService.existsById(courseId)).thenReturn(true);

        // Выполнение
        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        // Проверка
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService, times(1)).deleteCourse(courseId);
    }

    @Test
    public void testDeleteCourse_NotFound() {
        Long courseId = 2L;

        // Настраиваем мок, чтобы курса не существовало
        when(courseService.existsById(courseId)).thenReturn(false);

        // Выполнение
        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        // Проверка
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(courseService, never()).deleteCourse(courseId);
    }


}