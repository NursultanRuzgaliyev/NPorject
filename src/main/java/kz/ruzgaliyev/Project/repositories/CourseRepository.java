package kz.ruzgaliyev.Project.repositories;

import kz.ruzgaliyev.Project.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findAllById(Long id);
    boolean existsById(Long id);
}
