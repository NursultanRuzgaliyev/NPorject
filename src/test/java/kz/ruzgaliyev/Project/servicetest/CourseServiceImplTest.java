package kz.ruzgaliyev.Project.servicetest;

import kz.ruzgaliyev.Project.dtos.CourseDTO;
import kz.ruzgaliyev.Project.entities.Course;
import kz.ruzgaliyev.Project.exception.ResourceNotFoundException;
import kz.ruzgaliyev.Project.mapper.CourseMapper;
import kz.ruzgaliyev.Project.repositories.CourseRepository;
import kz.ruzgaliyev.Project.services.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        List<CourseDTO> courseDTOs = new ArrayList<>();
        courseDTOs.add(new CourseDTO());
        when(courseMapper.mapToDTO(courses)).thenReturn(courseDTOs);

        List<CourseDTO> result = courseService.getAllCourses();
        assertEquals(1, result.size());
        verify(courseRepository, times(1)).findAll();
        verify(courseMapper, times(1)).mapToDTO(courses);
    }

    @Test
    public void testGetCourseById_Success() {
        Long courseId = 1L;
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        CourseDTO courseDTO = new CourseDTO();
        when(courseMapper.mapToDTO(course)).thenReturn(courseDTO);

        CourseDTO result = courseService.getCourseById(courseId);
        assertEquals(courseDTO, result);
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseMapper, times(1)).mapToDTO(course);
    }

    @Test
    public void testGetCourseById_NotFound() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.getCourseById(courseId));
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    public void testCreateCourse() {
        CourseDTO courseDTO = new CourseDTO();
        Course course = new Course();
        when(courseMapper.mapToEntity(courseDTO)).thenReturn(course);

        courseService.createCourse(courseDTO);
        verify(courseRepository, times(1)).save(course);
        verify(courseMapper, times(1)).mapToEntity(courseDTO);
    }

    @Test
    public void testUpdateCourse_Success() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("Updated Course");

        Course course = new Course();
        when(courseRepository.findById(courseDTO.getId())).thenReturn(Optional.of(course));

        courseService.updateCourse(courseDTO);
        assertEquals("Updated Course", course.getName());
        verify(courseRepository, times(1)).findById(courseDTO.getId());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testUpdateCourse_NotFound() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        when(courseRepository.findById(courseDTO.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.updateCourse(courseDTO));
        verify(courseRepository, times(1)).findById(courseDTO.getId());
    }

    @Test
    public void testDeleteCourse_Success() {
        Long courseId = 1L;
        when(courseRepository.existsById(courseId)).thenReturn(true);

        courseService.deleteCourse(courseId);
        verify(courseRepository, times(1)).deleteById(courseId);
        verify(courseRepository, times(1)).existsById(courseId);
    }

    @Test
    public void testDeleteCourse_NotFound() {
        Long courseId = 1L;
        when(courseRepository.existsById(courseId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse(courseId));
        verify(courseRepository, times(1)).existsById(courseId);
    }
}