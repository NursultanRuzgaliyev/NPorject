package kz.ruzgaliyev.Project.mapper;

import kz.ruzgaliyev.Project.dtos.AttachmentDTO;
import kz.ruzgaliyev.Project.entities.Attachment;
import org.mapstruct.Mapper;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    List<AttachmentDTO> toDTOList(List<Attachment> attachments);
    AttachmentDTO toDTO(Attachment attachment);
    Attachment toAttachment(AttachmentDTO attachmentDTO);
}
