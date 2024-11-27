package kz.ruzgaliyev.Project.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Data
public class UserUpdateDTO {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password; // Новый пароль, если требуется обновление
}
