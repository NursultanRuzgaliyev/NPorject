package kz.ruzgaliyev.Project.servicetest;


import kz.ruzgaliyev.Project.dtos.ChapterDTO;
import kz.ruzgaliyev.Project.entities.Chapter;
import kz.ruzgaliyev.Project.exception.ResourceNotFoundException;
import kz.ruzgaliyev.Project.mapper.ChapterMapper;
import kz.ruzgaliyev.Project.repositories.ChapterRepository;
import kz.ruzgaliyev.Project.services.impl.ChapterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChapterServiceImplTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @InjectMocks
    private ChapterServiceImpl chapterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllChapters_ShouldReturnEmptyList_WhenNoChaptersExist() {
        when(chapterRepository.findAll()).thenReturn(Collections.emptyList());
        when(chapterMapper.mapToDTO(Collections.emptyList())).thenReturn(Collections.emptyList());

        var result = chapterService.getAllChapters();

        assertTrue(result.isEmpty(), "The result should be an empty list");
        verify(chapterRepository, times(1)).findAll();
    }

    @Test
    void getChapterById_ShouldReturnChapter_WhenChapterExists() {
        Chapter chapter = new Chapter();
        chapter.setId(1L);
        ChapterDTO chapterDTO = new ChapterDTO();
        chapterDTO.setId(1L);

        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(chapterMapper.mapToDTO(chapter)).thenReturn(chapterDTO);

        var result = chapterService.getChapterById(1L);

        assertNotNull(result, "ChapterDTO should not be null");
        assertEquals(1L, result.getId(), "Chapter ID should match");
        verify(chapterRepository, times(1)).findById(1L);
    }

    @Test
    void getChapterById_ShouldThrowException_WhenChapterDoesNotExist() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> chapterService.getChapterById(1L));
        verify(chapterRepository, times(1)).findById(1L);
    }

    @Test
    void createChapter_ShouldSaveChapter() {
        ChapterDTO chapterDTO = new ChapterDTO();
        chapterDTO.setId(1L);
        Chapter chapter = new Chapter();

        when(chapterMapper.mapToEntity(chapterDTO)).thenReturn(chapter);

        chapterService.createChapter(chapterDTO);

        verify(chapterRepository, times(1)).save(chapter);
    }

    @Test
    void updateChapter_ShouldUpdateChapter_WhenChapterExists() {
        ChapterDTO chapterDTO = new ChapterDTO();
        chapterDTO.setId(1L);
        Chapter chapter = new Chapter();

        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));

        chapterService.updateChapter(chapterDTO);

        verify(chapterRepository, times(1)).save(chapter);
    }

    @Test
    void deleteChapter_ShouldDeleteChapter_WhenChapterExists() {
        when(chapterRepository.existsById(1L)).thenReturn(true);

        chapterService.deleteChapter(1L);

        verify(chapterRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteChapter_ShouldThrowException_WhenChapterDoesNotExist() {
        when(chapterRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> chapterService.deleteChapter(1L));
        verify(chapterRepository, times(1)).existsById(1L);
    }
}