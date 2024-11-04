package kz.ruzgaliyev.Project.services;

import kz.ruzgaliyev.Project.dtos.ChapterDTO;

import java.util.List;

public interface ChapterService {
    List<ChapterDTO> getAllChapters();
    ChapterDTO getChapterById(Long id);
    void createChapter(ChapterDTO chapterDTO);
    void updateChapter(ChapterDTO chapterDTO);
    void deleteChapter(Long id);
}
