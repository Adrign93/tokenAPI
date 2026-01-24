package com.adrign93.tokenGenerator.service;


import com.adrign93.tokenGenerator.domain.entity.User;
import com.adrign93.tokenGenerator.exception.ResourceNotFoundException;
import com.adrign93.tokenGenerator.repository.UserRepository;
import com.adrign93.tokenGenerator.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindByUsername() {
        Optional<User> user = Optional.of(User.builder()
                        .username("username")
                        .password("password")
                        .entity("entity")
                .build());
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        User result = userService.findByUsername("username");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("username", result.getUsername());
        Assertions.assertEquals("password", result.getPassword());
        Assertions.assertEquals("entity", result.getEntity());
    }

    @Test
    void testFindByUsernameException() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.findByUsername("username"));
    }

    @Test
    void testValidateUser() {
        Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .entity("entity")
                .build());
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        Assertions.assertDoesNotThrow(() -> userService.validateUser(TestUtils.generateTokenRequest()));
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("username");
    }

    @Test
    void testValidateUserException() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.validateUser(TestUtils.generateTokenRequest()));
    }
}