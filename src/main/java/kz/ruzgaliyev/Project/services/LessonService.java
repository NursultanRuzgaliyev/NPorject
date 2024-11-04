package kz.ruzgaliyev.Project.services;

import kz.ruzgaliyev.Project.dtos.LessonDTO;

import java.util.List;

public interface LessonService {
    List<LessonDTO> getAllLessons();
    LessonDTO getLessonById(Long id);
    void createLesson(LessonDTO lessonDTO);
    void updateLesson(LessonDTO lessonDTO);
    void deleteLesson(Long id);
}
