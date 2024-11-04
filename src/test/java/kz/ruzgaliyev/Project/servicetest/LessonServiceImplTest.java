package kz.ruzgaliyev.Project.servicetest;

import kz.ruzgaliyev.Project.dtos.LessonDTO;
import kz.ruzgaliyev.Project.entities.Lesson;
import kz.ruzgaliyev.Project.exception.ResourceNotFoundException;
import kz.ruzgaliyev.Project.mapper.LessonMapper;
import kz.ruzgaliyev.Project.repositories.LessonRepository;
import kz.ruzgaliyev.Project.services.impl.LessonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLessons_ShouldReturnEmptyList_WhenNoLessonsExist() {
        when(lessonRepository.findAll()).thenReturn(Collections.emptyList());
        when(lessonMapper.mapToDTO(Collections.emptyList())).thenReturn(Collections.emptyList());

        var result = lessonService.getAllLessons();

        assertTrue(result.isEmpty(), "The result should be an empty list");
        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    void getLessonById_ShouldReturnLesson_WhenLessonExists() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(1L);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonMapper.mapToDTO(lesson)).thenReturn(lessonDTO);

        var result = lessonService.getLessonById(1L);

        assertNotNull(result, "LessonDTO should not be null");
        assertEquals(1L, result.getId(), "Lesson ID should match");
        verify(lessonRepository, times(1)).findById(1L);
    }

    @Test
    void getLessonById_ShouldThrowException_WhenLessonDoesNotExist() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> lessonService.getLessonById(1L));
        verify(lessonRepository, times(1)).findById(1L);
    }

    @Test
    void createLesson_ShouldSaveLesson() {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(1L);
        Lesson lesson = new Lesson();

        when(lessonMapper.mapToEntity(lessonDTO)).thenReturn(lesson);

        lessonService.createLesson(lessonDTO);

        verify(lessonRepository, times(1)).save(lesson);
    }

    @Test
    void updateLesson_ShouldUpdateLesson_WhenLessonExists() {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(1L);
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        lessonService.updateLesson(lessonDTO);

        verify(lessonRepository, times(1)).save(lesson);
    }

    @Test
    void updateLesson_ShouldThrowException_WhenLessonDoesNotExist() {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(1L);

        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> lessonService.updateLesson(lessonDTO));
        verify(lessonRepository, times(1)).findById(1L);
    }

    @Test
    void deleteLesson_ShouldDeleteLesson_WhenLessonExists() {
        when(lessonRepository.existsById(1L)).thenReturn(true);

        lessonService.deleteLesson(1L);

        verify(lessonRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteLesson_ShouldThrowException_WhenLessonDoesNotExist() {
        when(lessonRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> lessonService.deleteLesson(1L));
        verify(lessonRepository, times(1)).existsById(1L);
    }
}

