package kz.ruzgaliyev.Project.controllers;

import kz.ruzgaliyev.Project.dtos.UserCreateDto;
import kz.ruzgaliyev.Project.dtos.UserSignInDTO;
import kz.ruzgaliyev.Project.services.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final KeycloakService keycloakService;
    @PostMapping(value = "/create")
    public UserRepresentation createUser(@RequestBody UserCreateDto userCreateDto) {
        return keycloakService.createUser(userCreateDto);
    }
    @PostMapping(value = "/sign-in")
    public String signIn(@RequestBody UserSignInDTO userSignInDto) {
        return keycloakService.signIn(userSignInDto);
    }

}
