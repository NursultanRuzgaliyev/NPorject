package kz.ruzgaliyev.Project.repositories;

import kz.ruzgaliyev.Project.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findAllByLessonId(Long lessonId);
}
