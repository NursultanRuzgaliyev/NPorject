package kz.ruzgaliyev.Project.dtos;

import kz.ruzgaliyev.Project.entities.Lesson;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentDTO {

    private Long id;
    private String name;
    private String url;
    private Lesson lesson;
    private LocalDateTime createdTime;
}
