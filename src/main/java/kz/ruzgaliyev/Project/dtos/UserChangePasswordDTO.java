package kz.ruzgaliyev.Project.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserChangePasswordDTO {
    private String newPassword;
}
