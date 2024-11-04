package kz.ruzgaliyev.Project.services;

import kz.ruzgaliyev.Project.dtos.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    CourseDTO getCourseById(Long id);
    void createCourse(CourseDTO courseDTO);
    void updateCourse(CourseDTO courseDTO);
    void deleteCourse(Long id);
    boolean existsById(Long id);

}
