package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.Repository.UserReopository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserReopository userReopository;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser() {
        User mockUser = User.builder().email("devkmandal0@gmail.com").name("dev").build();
        when(userReopository.findByEmail("devkmandal0@gmail.com")).thenReturn(Optional.ofNullable(mockUser));
        Optional<User> result = userReopository.findByEmail("devkmandal0@gmail.com");
        assertEquals("devkmandal0@gmail.com",result.get().getEmail());
    }
}