package kz.ruzgaliyev.Project.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.ruzgaliyev.Project.controllers.LessonController;
import kz.ruzgaliyev.Project.dtos.LessonDTO;
import kz.ruzgaliyev.Project.services.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LessonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private LessonController lessonController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lessonController).build();
    }

    @Test
    void testGetAllLessons() throws Exception {
        LessonDTO lesson1 = new LessonDTO(); // Заполните поля lesson1
        LessonDTO lesson2 = new LessonDTO(); // Заполните поля lesson2
        List<LessonDTO> lessons = Arrays.asList(lesson1, lesson2);

        when(lessonService.getAllLessons()).thenReturn(lessons);

        mockMvc.perform(get("/lesson"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetLessonById() throws Exception {
        Long lessonId = 1L;
        LessonDTO lesson = new LessonDTO(); // Заполните поля lesson

        when(lessonService.getLessonById(lessonId)).thenReturn(lesson);

        mockMvc.perform(get("/lesson/{id}", lessonId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // Добавьте дополнительные проверки для полей lesson
    }

    @Test
    void testCreateLesson() throws Exception {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setTitle("New Lesson"); // Убедитесь, что заполняете все необходимые поля

        doNothing().when(lessonService).createLesson(any(LessonDTO.class)); // Мокируем вызов метода

        mockMvc.perform(post("/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(lessonDTO)))
                .andExpect(status().isOk()); // Ожидаем 200 OK
    }



    @Test
    void testUpdateLesson() throws Exception {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setTitle("Updated Lesson"); // Заполняйте все необходимые поля
        lessonDTO.setId(1L); // Если необходимо, добавьте идентификатор для обновления

        mockMvc.perform(put("/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(lessonDTO)))
                .andExpect(status().isOk()); // Ожидаем 200 OK
    }
git i

    @Test
    void testDeleteLesson() throws Exception {
        Long lessonId = 1L;

        mockMvc.perform(delete("/lesson/{id}", lessonId))
                .andExpect(status().isNoContent());

        verify(lessonService, times(1)).deleteLesson(lessonId);
    }
}
