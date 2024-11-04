package kz.ruzgaliyev.Project.controllers;

import kz.ruzgaliyev.Project.dtos.LessonDTO;
import kz.ruzgaliyev.Project.services.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@Tag(name = "Lesson Controller", description = "Operations related to lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    @Operation(summary = "Get all lessons", description = "Retrieve a list of all lessons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all lessons"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<LessonDTO> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lesson by ID", description = "Retrieve lesson details by lesson ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved lesson"),
            @ApiResponse(responseCode = "404", description = "Lesson not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public LessonDTO getLessonById(@PathVariable("id") Long id) {
        return lessonService.getLessonById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new lesson", description = "Add a new lesson to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void createLesson(@RequestBody LessonDTO lessonDTO) {
        lessonService.createLesson(lessonDTO);
    }

    @PutMapping
    @Operation(summary = "Update an existing lesson", description = "Update the details of an existing lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Lesson not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateLesson(@RequestBody LessonDTO lessonDTO) {
        lessonService.updateLesson(lessonDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lesson by ID", description = "Remove a lesson from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lesson not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build(); // Возвращаем 204 No Content
    }
}
