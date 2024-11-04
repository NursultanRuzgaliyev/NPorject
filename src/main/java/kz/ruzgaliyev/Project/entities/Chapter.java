package kz.ruzgaliyev.Project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chapters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "\"order\"")
    private int order;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
