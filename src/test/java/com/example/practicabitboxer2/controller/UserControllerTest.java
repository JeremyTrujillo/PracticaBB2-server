package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.JwtDTO;
import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.security.CustomUserDetails;
import com.example.practicabitboxer2.security.JwtGenerator;
import com.example.practicabitboxer2.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

import static com.example.practicabitboxer2.utils.TestUtils.*;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtGenerator jwtGenerator;

    @BeforeEach
    void config() {
        userController = new UserController(userService, authenticationManager, jwtGenerator);
    }

    @Test
    void loginInvalidUser() {
        UserDTO emptyUsernameUser = firstUser().withUsername(null).build();
        UserDTO invalidUsernameUser = firstUser().withUsername("").build();
        UserDTO emptyPasswordUser = firstUser().withPassword(null).build();
        UserDTO invalidPasswordUser = firstUser().withPassword("").build();
        assertThrows(UserEmptyException.class,
                () -> userController.login(null));
        assertThrows(UserEmptyUsernameException.class,
                () -> userController.login(emptyUsernameUser));
        assertThrows(UserEmptyUsernameException.class,
                () -> userController.login(invalidUsernameUser));
        assertThrows(UserInvalidPasswordException.class,
                () -> userController.login(emptyPasswordUser));
        assertThrows(UserInvalidPasswordException.class,
                () -> userController.login(invalidPasswordUser));
    }

    @Test
    void login() {
        UserDTO user = firstUser().build();
        List<GrantedAuthority> roles = new java.util.ArrayList<>(Collections.emptyList());
        roles.add(new SimpleGrantedAuthority(user.getRole()));
        CustomUserDetails customUserDetails = new CustomUserDetails(user.getUsername(), user.getPassword(), roles);
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        when(authenticationMock.getPrincipal()).thenReturn(customUserDetails);
        when(authenticationManager.authenticate(any())).thenReturn(authenticationMock);
        ResponseEntity<JwtDTO> login = userController.login(user);
        JwtDTO jwt = login.getBody();
        assertNotNull(jwt);
        assertEquals(user.getUsername(),jwt.getUsername());
        assertEquals(roles, jwt.getRoles());
        HttpStatus statusCode = login.getStatusCode();
        assertEquals(OK, statusCode);
    }

    @Test
    void findAll() {
        List<UserDTO> testList = newArrayList(firstUser().build(), secondUser().build());
        when(userService.findAll()).thenReturn(testList);
        ResponseEntity<List<UserDTO>> findAll = userController.findAll();
        List<UserDTO> all = findAll.getBody();
        assertNotNull(all);
        assertArrayEquals(testList.toArray(), all.toArray());
        HttpStatus statusCode = findAll.getStatusCode();
        assertEquals(OK, statusCode);
    }

    @Test
    void createInvalidUser() {
        UserDTO nullUsernameUser = firstUser().withUsername(null).build();
        UserDTO invalidUsernameUser = firstUser().withUsername("").build();
        UserDTO nullPasswordUser = firstUser().withPassword(null).build();
        UserDTO invalidPasswordUser = firstUser().withPassword("").build();
        UserDTO nullRoleUser = firstUser().withRole(null).build();
        UserDTO invalidRoleUser = firstUser().withRole("TEST").build();
        assertThrows(UserEmptyException.class,
                () -> userController.createUser(null));
        assertThrows(UserEmptyUsernameException.class,
                () -> userController.createUser(nullUsernameUser));
        assertThrows(UserEmptyUsernameException.class,
                () -> userController.createUser(invalidUsernameUser));
        assertThrows(UserInvalidPasswordException.class,
                () -> userController.createUser(nullPasswordUser));
        assertThrows(UserInvalidPasswordException.class,
                () -> userController.createUser(invalidPasswordUser));
        assertThrows(UserInvalidRoleException.class,
                () -> userController.createUser(nullRoleUser));
        assertThrows(UserInvalidRoleException.class,
                () -> userController.createUser(invalidRoleUser));
    }

    @Test
    void createAlreadyExistentUser() {
        UserDTO firstUser = firstUser().build();
        when(userService.findByUsername(firstUser.getUsername())).thenReturn(firstUser);
        assertThrows(UserUsernameAlreadyExistsException.class,
                () ->userController.createUser(firstUser));
    }

    @Test
    void createValidUser() {
        UserDTO newUser = newUser().build();
        ResponseEntity<Void> createUser = userController.createUser(newUser);
        HttpStatus statusCode = createUser.getStatusCode();
        assertEquals(HttpStatus.CREATED, statusCode);
    }

    @Test
    void deleteNonexistentUser() {
        UserDTO firstUser = firstUser().build();
        String username = firstUser.getUsername();
        when(userService.findByUsername(username)).thenReturn(null);
        assertThrows(UserNotFoundException.class,
                () -> userController.deleteByUsername(username));
    }

    @Test
    void deleteValidUser() {
        UserDTO firstUser = firstUser().build();
        when(userService.findByUsername(firstUser.getUsername())).thenReturn(firstUser);
        HttpStatus statusCode = userController.deleteByUsername(firstUser.getUsername())
                .getStatusCode();
        assertEquals(OK, statusCode);
    }
}