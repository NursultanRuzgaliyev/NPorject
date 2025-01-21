package kz.ruzgaliyev.Project.services;

import kz.ruzgaliyev.Project.dtos.AttachmentDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    String uploadFile(MultipartFile file);
    List<AttachmentDTO> getFileList();
    ByteArrayResource downloadFile(String fileName);
}
