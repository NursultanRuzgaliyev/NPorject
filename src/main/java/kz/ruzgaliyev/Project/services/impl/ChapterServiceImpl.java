package kz.ruzgaliyev.Project.services.impl;

import kz.ruzgaliyev.Project.dtos.ChapterDTO;
import kz.ruzgaliyev.Project.entities.Chapter;
import kz.ruzgaliyev.Project.exception.ResourceNotFoundException;
import kz.ruzgaliyev.Project.mapper.ChapterMapper;
import kz.ruzgaliyev.Project.repositories.ChapterRepository;
import kz.ruzgaliyev.Project.services.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    private static final Logger logger = LoggerFactory.getLogger(ChapterServiceImpl.class);

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private ChapterService chapterService;
    @Override
    public List<ChapterDTO> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();
        logger.info("Retrieved all chapters: {}", chapters.size());
        return chapterMapper.mapToDTO(chapters);
    }

    @Override
    public ChapterDTO getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Chapter not found with ID: {}", id);
                    return new ResourceNotFoundException("Глава не найдена с ID: " + id);
                });
        logger.debug("Retrieved chapter: {}", chapter);
        return chapterMapper.mapToDTO(chapter);
    }

    @Override
    public void createChapter(ChapterDTO chapterDTO) {
        Chapter chapter = chapterMapper.mapToEntity(chapterDTO);
        chapterRepository.save(chapter);
        logger.debug("Created new chapter: {}", chapter);
    }

    @Override
    public void updateChapter(ChapterDTO chapterDTO) {
        Chapter chapter = chapterRepository.findById(chapterDTO.getId())
                .orElseThrow(() -> {
                    logger.error("Chapter not found with ID: {}", chapterDTO.getId());
                    return new ResourceNotFoundException("Глава не найдена с ID: " + chapterDTO.getId());
                });

        chapter.setName(chapterDTO.getName());
        chapter.setCourse(chapterDTO.getCourse());
        chapter.setDescription(chapterDTO.getDescription());
        chapter.setCreatedTime(chapterDTO.getCreatedTime());
        chapter.setUpdatedTime(chapterDTO.getUpdatedTime());
        chapter.setOrder(chapterDTO.getOrder());
        chapterRepository.save(chapter);
        logger.debug("Updated chapter: {}", chapter);
    }

    @Override
    public void deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) {
            logger.error("Chapter not found with ID: {}", id);
            throw new ResourceNotFoundException("Глава не найдена с ID: " + id);
        }
        chapterRepository.deleteById(id);
        logger.info("Deleted chapter with ID: {}", id);
    }
}
