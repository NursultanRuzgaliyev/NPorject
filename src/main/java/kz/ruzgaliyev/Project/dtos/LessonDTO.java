package kz.ruzgaliyev.Project.dtos;

import kz.ruzgaliyev.Project.entities.Chapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonDTO {
    private Long id;
    private  String name;
    private  String description;
    private String content;
    private int order;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Chapter chapter;

    public void setTitle(String newLesson) {
        this.name = newLesson;
    }

}
