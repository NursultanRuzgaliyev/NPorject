package kz.ruzgaliyev.Project.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Конвертер для извлечения ролей из JWT токена Keycloak и их преобразования
 * в GrantedAuthority для использования Spring Security.
 */
public class KeycloakRoleConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        UserDetails userDetails;
         // Извлечение авторизаций из токена
        Collection<GrantedAuthority> authorities = extractAuthorities(source);
        return new JwtAuthenticationToken(source, authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt source) {
        // Извлечение раздела "realm_access" из claim токена
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");

        if (realmAccess == null || realmAccess.isEmpty()) {
            // Если "realm_access" отсутствует, возвращается пустая коллекция
            return Collections.emptyList();
        }

        // Извлечение списка ролей из "realm_access"
        List<String> roles = (List<String>) realmAccess.get("roles");

        if (roles == null || roles.isEmpty()) {
            // Если список ролей пустой, возвращается пустая коллекция
            return Collections.emptyList();
        }

        // Преобразование ролей в SimpleGrantedAuthority с префиксом "ROLE_"
        return roles.stream()
                .map(roleName -> "ROLE_" + roleName) // Префикс для совместимости с Spring Security
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
