package kz.ruzgaliyev.Project.services;

import kz.ruzgaliyev.Project.dtos.UserCreateDto;
import kz.ruzgaliyev.Project.dtos.UserSignInDTO;
import kz.ruzgaliyev.Project.dtos.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    private final RestTemplate restTemplate;

    @Value("${keycloak.url}")
    private String url;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client}")
    private String client;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public UserRepresentation createUser(UserCreateDto userCreateDto) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setEmail(userCreateDto.getEmail());
        newUser.setEmailVerified(true);
        newUser.setUsername(userCreateDto.getUsername());
        newUser.setFirstName(userCreateDto.getFirstName());
        newUser.setLastName(userCreateDto.getLastName());
        newUser.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userCreateDto.getPassword());
        credential.setTemporary(false);

        newUser.setCredentials(List.of(credential));

        Response response = keycloak
                .realm(realm)
                .users()
                .create(newUser);

        if (response.getStatus() != HttpStatus.CREATED.value()) { // 201 status
            log.error("Error on creating user");
            throw new RuntimeException("Failed to create user!");
        }

        List<UserRepresentation> searchUser = keycloak.realm(realm).users().search(userCreateDto.getUsername());
        return searchUser.get(0);
    }

    public String signIn(UserSignInDTO userSignInDto) {
        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", client);
        formData.add("client_secret", clientSecret);
        formData.add("username", userSignInDto.getUsername());
        formData.add("password", userSignInDto.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            log.error("Error on signIn");
            throw new RuntimeException("Failed to authenticate");
        }
        return (String) responseBody.get("access_token");

    }
    public  void changePassword(String username,String newPassword) {
        List<UserRepresentation>  users = keycloak
                .realm(realm)
                .users()
                .search(username);
        if(users.isEmpty()){
            log.error("User not found to change password");
            throw new RuntimeException("User not found");
        }
        UserRepresentation userRepresentation = users.get(0);
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(newPassword);
        credentialRepresentation.setTemporary(false);


        keycloak
                .realm(realm)
                .users()
                .get(userRepresentation.getId())
                .resetPassword(credentialRepresentation);
        log.info("Changed password");
    }
    public String refreshToken(String refreshToken) {
        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", client);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            log.error("Error refreshing token");
            throw new RuntimeException("Failed to refresh token");
        }

        return (String) responseBody.get("access_token");
    }
    public void updateUser(String currentUserName, UserUpdateDTO userUpdateDTO) {
        // Получаем пользователя из Keycloak
        List<UserRepresentation> users = keycloak
                .realm(realm)
                .users()
                .search(currentUserName);

        if (users.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserRepresentation user = users.get(0);

        // Обновляем данные
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getUsername() != null) {
            user.setUsername(userUpdateDTO.getUsername());
        }
        if (userUpdateDTO.getFirstName() != null) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null) {
            user.setLastName(userUpdateDTO.getLastName());
        }

        // Обновляем пользователя в Keycloak
        keycloak
                .realm(realm)
                .users()
                .get(user.getId())
                .update(user);

        // Если передан новый пароль, обновляем его отдельно
        if (userUpdateDTO.getPassword() != null) {
            CredentialRepresentation newPassword = new CredentialRepresentation();
            newPassword.setType(CredentialRepresentation.PASSWORD);
            newPassword.setValue(userUpdateDTO.getPassword());
            newPassword.setTemporary(false);

            keycloak
                    .realm(realm)
                    .users()
                    .get(user.getId())
                    .resetPassword(newPassword);
        }
    }


}
