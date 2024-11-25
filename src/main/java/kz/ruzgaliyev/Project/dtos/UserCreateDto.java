package kz.ruzgaliyev.Project.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateDto {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
