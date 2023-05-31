package com.example.practicabitboxer2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class JwtDTO {
    private String token;
    private static final String BEARER = "BEARER";
    private Long id;
    private String username;
    private Collection<? extends GrantedAuthority> roles;
}
