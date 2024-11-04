package kz.ruzgaliyev.Project.dtos;

import kz.ruzgaliyev.Project.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChapterDTO {
    private Long id;
    private String name;
    private String description;
    private Course course;
    private int order;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public void setTitle(String title) {
        this.name = title;
    }

    public String getTitle() {
        return this.name;
    }
}
