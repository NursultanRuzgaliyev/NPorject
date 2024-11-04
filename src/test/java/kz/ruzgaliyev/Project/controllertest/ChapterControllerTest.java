package kz.ruzgaliyev.Project.controllertest;

import kz.ruzgaliyev.Project.controllers.ChapterController;
import kz.ruzgaliyev.Project.dtos.ChapterDTO;
import kz.ruzgaliyev.Project.services.ChapterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChapterControllerTest {

    @InjectMocks
    private ChapterController chapterController;

    @Mock
    private ChapterService chapterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllChapters() {
        // Mock data
        ChapterDTO chapter1 = new ChapterDTO();
        chapter1.setId(1L);
        chapter1.setTitle("Chapter 1");

        ChapterDTO chapter2 = new ChapterDTO();
        chapter2.setId(2L);
        chapter2.setTitle("Chapter 2");

        when(chapterService.getAllChapters()).thenReturn(Arrays.asList(chapter1, chapter2));

        // Call method
        ResponseEntity<List<ChapterDTO>> response = chapterController.getAllChapters();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetChapterById_Found() {
        // Mock data
        ChapterDTO chapter = new ChapterDTO();
        chapter.setId(1L);
        chapter.setTitle("Chapter 1");

        when(chapterService.getChapterById(1L)).thenReturn(chapter);

        // Call method
        ResponseEntity<ChapterDTO> response = chapterController.getChapterById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Chapter 1", response.getBody().getTitle());
    }

    @Test
    void testGetChapterById_NotFound() {
        when(chapterService.getChapterById(1L)).thenReturn(null);

        // Call method
        ResponseEntity<ChapterDTO> response = chapterController.getChapterById(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateChapter() {
        // Mock data
        ChapterDTO chapter = new ChapterDTO();
        chapter.setTitle("New Chapter");

        doNothing().when(chapterService).createChapter(chapter);

        // Call method
        ResponseEntity<Void> response = chapterController.createChapter(chapter);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testUpdateChapter() {
        // Mock data
        ChapterDTO chapter = new ChapterDTO();
        chapter.setId(1L);
        chapter.setTitle("Updated Chapter");

        doNothing().when(chapterService).updateChapter(chapter);

        // Call method
        ResponseEntity<Void> response = chapterController.updateChapter(1L, chapter);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteChapter_Found() {
        when(chapterService.getChapterById(1L)).thenReturn(new ChapterDTO());
        doNothing().when(chapterService).deleteChapter(1L);

        // Call method
        ResponseEntity<Void> response = chapterController.deleteChapter(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteChapter_NotFound() {
        when(chapterService.getChapterById(1L)).thenReturn(null);

        // Call method
        ResponseEntity<Void> response = chapterController.deleteChapter(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }
}
