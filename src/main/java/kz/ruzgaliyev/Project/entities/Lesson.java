package kz.ruzgaliyev.Project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    private  String description;
    private String content;
    @Column(name = "\"order\"")
    private int order;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    @ManyToOne(fetch = FetchType.EAGER)
    private Chapter chapter;
}