package kz.ruzgaliyev.Project.mapper;

import kz.ruzgaliyev.Project.dtos.CourseDTO;
import kz.ruzgaliyev.Project.entities.Course;
import org.mapstruct.Mapper;
import java.util.List;
@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO mapToDTO(Course courses);
    Course mapToEntity(CourseDTO courseDTO);
    List<CourseDTO> mapToDTO(List<Course> courses);
    List<Course> mapToEntity(List<CourseDTO> courseDTOS);

}
