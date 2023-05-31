package com.example.practicabitboxer2.security;

import com.example.practicabitboxer2.exceptions.UserNotFoundException;
import com.example.practicabitboxer2.model.Role;
import com.example.practicabitboxer2.model.User;
import com.example.practicabitboxer2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService implements UserDetailsService {

    private final @NonNull UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Role role) {
        List<GrantedAuthority> roles = new java.util.ArrayList<>(Collections.emptyList());
        roles.add(new SimpleGrantedAuthority(role.getName()));
        return roles;
    }
}
