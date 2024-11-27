package kz.ruzgaliyev.Project.controllers;

import kz.ruzgaliyev.Project.dtos.*;
import kz.ruzgaliyev.Project.services.KeycloakService;
import kz.ruzgaliyev.Project.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final KeycloakService keycloakService;
    private final Keycloak keycloak;

    @PostMapping(value = "/create")
    public UserRepresentation createUser(@RequestBody UserCreateDto userCreateDto) {
        return keycloakService.createUser(userCreateDto);
    }
    @PostMapping(value = "/sign-in")

    public String signIn(@RequestBody UserSignInDTO userSignInDto) {
        return keycloakService.signIn(userSignInDto);
    }
    @PostMapping(value = "/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity changePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        String currentUserName = UserUtils.getCurrentUserName();
        if(currentUserName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Couldn't Identify User");
        }
        try{
            keycloakService.changePassword(currentUserName,userChangePasswordDTO.getNewPassword());
            return ResponseEntity.ok("Password changed succesfully");
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error on change password");
        }
    }
    @PostMapping(value = "/refresh-token")
    public ResponseEntity refreshToken(@RequestBody TokenRefreshDTO tokenRefreshDTO) {
        try {
            String newToken = keycloakService.refreshToken(tokenRefreshDTO.getRefreshToken());
            return ResponseEntity.ok().body(newToken);
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to refresh token: " + e.getMessage());
        }
    }
    @PostMapping(value = "/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        String currentUserName = UserUtils.getCurrentUserName(); // Определяем текущего пользователя
        if (currentUserName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Couldn't Identify User");
        }
        try {
            keycloakService.updateUser(currentUserName, userUpdateDTO);
            return ResponseEntity.ok("User data updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error on updating user data: " + e.getMessage());
        }
    }


}
