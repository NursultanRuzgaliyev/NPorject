package kz.ruzgaliyev.Project.services.impl;

import kz.ruzgaliyev.Project.dtos.LessonDTO;
import kz.ruzgaliyev.Project.entities.Lesson;
import kz.ruzgaliyev.Project.exception.ResourceNotFoundException;
import kz.ruzgaliyev.Project.mapper.LessonMapper;
import kz.ruzgaliyev.Project.repositories.LessonRepository;
import kz.ruzgaliyev.Project.services.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private static final Logger logger = LoggerFactory.getLogger(LessonServiceImpl.class);

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        logger.info("Retrieved all lessons: {}", lessons.size());
        return lessonMapper.mapToDTO(lessons);
    }

    @Override
    public LessonDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Lesson not found with ID: {}", id);
                    return new ResourceNotFoundException("Lesson not found with ID: " + id);
                });
        logger.debug("Retrieved lesson: {}", lesson);
        return lessonMapper.mapToDTO(lesson);
    }

    @Override
    public void createLesson(LessonDTO lessonDTO) {
        Lesson lesson = lessonMapper.mapToEntity(lessonDTO);
        lessonRepository.save(lesson);
        logger.debug("Created new lesson: {}", lesson);
    }

    @Override
    public void updateLesson(LessonDTO lessonDTO) {
        Lesson lesson = lessonRepository.findById(lessonDTO.getId())
                .orElseThrow(() -> {
                    logger.error("Lesson not found with ID: {}", lessonDTO.getId());
                    return new ResourceNotFoundException("Lesson not found with ID: " + lessonDTO.getId());
                });

        lesson.setName(lessonDTO.getName());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setOrder(lessonDTO.getOrder());
        lesson.setContent(lessonDTO.getContent());
        lesson.setChapter(lessonDTO.getChapter());
        lesson.setCreatedTime(lessonDTO.getCreatedTime());
        lesson.setUpdatedTime(lessonDTO.getUpdatedTime());
        lessonRepository.save(lesson);
        logger.debug("Updated lesson: {}", lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            logger.error("Lesson not found with ID: {}", id);
            throw new ResourceNotFoundException("Lesson not found with ID: " + id);
        }
        lessonRepository.deleteById(id);
        logger.info("Deleted lesson with ID: {}", id);
    }
}
