package kz.ruzgaliyev.Project.controllers;

import kz.ruzgaliyev.Project.dtos.ChapterDTO;
import kz.ruzgaliyev.Project.services.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapter")
@Tag(name = "Chapter Controller", description = "Operations related to chapters")
public class ChapterController {

    private final ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping
    @Operation(summary = "Get all chapters", description = "Retrieve a list of all chapters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all chapters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ChapterDTO>> getAllChapters() {
        List<ChapterDTO> chapters = chapterService.getAllChapters();
        return ResponseEntity.ok(chapters);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get chapter by ID", description = "Retrieve chapter details by chapter ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved chapter"),
            @ApiResponse(responseCode = "404", description = "Chapter not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ChapterDTO> getChapterById(@PathVariable("id") Long id) {
        ChapterDTO chapter = chapterService.getChapterById(id);
        if (chapter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(chapter);
    }

    @PostMapping
    @Operation(summary = "Create a new chapter", description = "Add a new chapter to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chapter created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> createChapter(@RequestBody ChapterDTO chapterDTO) {
        chapterService.createChapter(chapterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing chapter", description = "Update the details of an existing chapter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chapter updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Chapter not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> updateChapter(@PathVariable("id") Long id, @RequestBody ChapterDTO chapterDTO) {
        chapterDTO.setId(id); // Устанавливаем ID в DTO для обновления
        chapterService.updateChapter(chapterDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a chapter by ID", description = "Remove a chapter from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Chapter deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Chapter not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteChapter(@PathVariable("id") Long id) {
        if (chapterService.getChapterById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Возвращаем 404, если глава не найдена
        }
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build(); // Возвращаем 204 No Content
    }
}
