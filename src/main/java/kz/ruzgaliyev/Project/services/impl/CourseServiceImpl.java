package kz.ruzgaliyev.Project.services.impl;

import kz.ruzgaliyev.Project.dtos.CourseDTO;
import kz.ruzgaliyev.Project.entities.Course;
import kz.ruzgaliyev.Project.exception.ResourceNotFoundException;
import kz.ruzgaliyev.Project.mapper.CourseMapper;
import kz.ruzgaliyev.Project.repositories.CourseRepository;
import kz.ruzgaliyev.Project.services.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        logger.info("Retrieved all courses: {}", courses.size());
        return courseMapper.mapToDTO(courses);
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", id);
                    return new ResourceNotFoundException("Курс не найден с ID: " + id);
                });
        logger.debug("Retrieved course: {}", course);
        return courseMapper.mapToDTO(course);
    }

    @Override
    public void createCourse(CourseDTO courseDTO) {
        Course course = courseMapper.mapToEntity(courseDTO);
        courseRepository.save(course);
        logger.debug("Created new course: {}", course);
    }

    @Override
    public void updateCourse(CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseDTO.getId())
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseDTO.getId());
                    return new ResourceNotFoundException("Курс не найден с ID: " + courseDTO.getId());
                });

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setCreatedTime(courseDTO.getCreatedTime());
        course.setUpdatedTime(courseDTO.getUpdatedTime());
        courseRepository.save(course);
        logger.debug("Updated course: {}", course);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            logger.error("Course not found with ID: {}", id);
            throw new ResourceNotFoundException("Курс не найден с ID: " + id);
        }
        courseRepository.deleteById(id);
        logger.info("Deleted course with ID: {}", id);
    }
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }
}
