package com.example.practicabitboxer2.services;

import com.example.practicabitboxer2.dtos.UserDTO;
import com.example.practicabitboxer2.model.User;
import com.example.practicabitboxer2.repositories.UserRepository;
import com.example.practicabitboxer2.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.example.practicabitboxer2.utils.TestUtils.firstUser;
import static com.example.practicabitboxer2.utils.TestUtils.secondUser;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void config() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void findById() {
        UserDTO first = firstUser().build();
        long nonexistentId = 3;
        when(userRepository.findById(first.getId())).thenReturn(Optional.of(UserUtils.dtoToEntity(first)));
        when(userRepository.findById(nonexistentId)).thenReturn(Optional.empty());
        assertEquals(first, userService.findById(first.getId()));
        assertNull(userService.findById(nonexistentId));
    }

    @Test
    void findByUsername() {
        UserDTO first = firstUser().build();
        when(userRepository.findByUsername(first.getUsername())).thenReturn(Optional.of(UserUtils.dtoToEntity(first)));
        when(userRepository.findByUsername("")).thenReturn(Optional.empty());
        assertEquals(first, userService.findByUsername(first.getUsername()));
        assertNull(userService.findByUsername(""));
    }

    @Test
    void findAll() {
        List<UserDTO> testList = newArrayList(firstUser().build(), secondUser().build());
        when(userRepository.findAll()).thenReturn(UserUtils.dtoToEntities(testList));
        List<UserDTO> all = userService.findAll();
        assertArrayEquals(all.toArray(), testList.toArray());
    }

    @Test
    void saveUser() {
        UserDTO firstUser = firstUser().build();
        String password = firstUser.getPassword();
        when(passwordEncoder.encode(password)).thenReturn("Encoded password");
        userService.saveUser(firstUser);
        User entity = UserUtils.dtoToEntity(firstUser);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(argumentCaptor.capture());
        User savedUser = argumentCaptor.getValue();
        assertEquals("Encoded password", savedUser.getPassword());
    }

    @Test
    void deleteByUsername() {
        UserDTO firstUser = firstUser().build();
        String username = firstUser.getUsername();
        userService.deleteByUsername(username);
        verify(userRepository, times(1)).deleteByUsername(username);
    }
}