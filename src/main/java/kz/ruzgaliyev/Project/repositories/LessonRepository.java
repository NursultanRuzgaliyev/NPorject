package kz.ruzgaliyev.Project.repositories;

import kz.ruzgaliyev.Project.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
 Lesson findAllById(Long id);
}
