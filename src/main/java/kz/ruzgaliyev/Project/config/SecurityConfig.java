package kz.ruzgaliyev.Project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.
                                requestMatchers("/user/create").permitAll().
                                requestMatchers("/user/sign-in").permitAll().
                                requestMatchers("/user/refresh").permitAll().
                                requestMatchers("/course/**").hasRole("ADMIN").
                                requestMatchers("/chapter/**").hasRole("ADMIN").
                                requestMatchers("/lesson/**").hasRole("ADMIN").
                                requestMatchers("file/upload").hasAnyRole("ADMIN", "TEACHER").
                                requestMatchers("/file/download/**").hasAnyRole("ADMIN", "TEACHER","USER").
                                anyRequest().authenticated()
                )
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(o -> o
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(keycloakRoleConverter())));
        return httpSecurity.build();
    }

    @Bean
    public KeycloakRoleConverter keycloakRoleConverter() {
        return new KeycloakRoleConverter();
    }
}
