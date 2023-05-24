package com.example.practicabitboxer2.controller;

import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.exceptions.*;
import com.example.practicabitboxer2.security.JwtGenerator;
import com.example.practicabitboxer2.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;

import static com.example.practicabitboxer2.utils.TestUtils.*;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
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
        assertThrows(UserEmptyException.class,
                () -> userController.createUser(null));
        UserDTO nullUsernameUser = firstUser().withUsername(null).build();
        assertThrows(UserEmptyUsernameException.class,
                () -> userController.createUser(nullUsernameUser));
        UserDTO invalidUsernameUser = firstUser().withUsername("").build();
        assertThrows(UserEmptyUsernameException.class,
                () -> userController.createUser(invalidUsernameUser));
        UserDTO nullPasswordUser = firstUser().withPassword(null).build();
        assertThrows(UserInvalidPasswordException.class,
                () -> userController.createUser(nullPasswordUser));
        UserDTO invalidPasswordUser = firstUser().withPassword("").build();
        assertThrows(UserInvalidPasswordException.class,
                () -> userController.createUser(invalidPasswordUser));
        UserDTO nullRoleUser = firstUser().withRole(null).build();
        assertThrows(UserInvalidRoleException.class,
                () -> userController.createUser(nullRoleUser));
        UserDTO invalidRoleUser = firstUser().withRole("TEST").build();
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