package kz.ruzgaliyev.Project.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSignInDTO {
    private String username;
    private String password;
}
