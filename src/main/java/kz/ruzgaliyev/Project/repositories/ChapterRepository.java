package kz.ruzgaliyev.Project.repositories;

import kz.ruzgaliyev.Project.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Chapter findAllById(Long id);
}
