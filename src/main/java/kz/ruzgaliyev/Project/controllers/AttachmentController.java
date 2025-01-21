package kz.ruzgaliyev.Project.controllers;

import kz.ruzgaliyev.Project.dtos.AttachmentDTO;
import kz.ruzgaliyev.Project.services.AttachmentService;
import kz.ruzgaliyev.Project.services.impl.AttachmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/file")
public class AttachmentController {

    private final AttachmentService attachmentService;
    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam(name = "file") MultipartFile file) {
       return  attachmentService.uploadFile(file);
    }
    @GetMapping(value = "/download/{file}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(name = "file") String fileName) {
        return ResponseEntity.ok()

                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filemame=\"" + fileName + "\"")
                .body(attachmentService.downloadFile(fileName));
    }
    @GetMapping(value = "/list")
    public List<AttachmentDTO> getFiles(){
        return attachmentService.getFileList();
    }
}
