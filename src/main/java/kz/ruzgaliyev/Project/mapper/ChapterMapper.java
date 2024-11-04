package kz.ruzgaliyev.Project.mapper;

import kz.ruzgaliyev.Project.dtos.ChapterDTO;
import kz.ruzgaliyev.Project.entities.Chapter;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ChapterMapper {
 ChapterDTO mapToDTO(Chapter chapter);
 Chapter mapToEntity(ChapterDTO chapterDTO);
 List<ChapterDTO> mapToDTO(List<Chapter> chapters);
 List<Chapter> mapToEntity(List<ChapterDTO> chapterDTOs);
}
