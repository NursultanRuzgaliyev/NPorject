package kz.ruzgaliyev.Project.mapper;

import kz.ruzgaliyev.Project.dtos.LessonDTO;
import kz.ruzgaliyev.Project.entities.Lesson;
import org.mapstruct.Mapper;
import java.util.List;
@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDTO mapToDTO(Lesson lessons);
    Lesson mapToEntity(LessonDTO lessonDTO);
    List<LessonDTO> mapToDTO(List<Lesson> lessons);
    List<Lesson> mapToEntity(List<LessonDTO> lessonDTO);
}
