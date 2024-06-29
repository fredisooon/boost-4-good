package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.user.Role;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.UserRepository;
import fyodor.dev.coremicroservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UUID userId;
    private String username;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        username = "testuser";
        user = new User();
        user.setId(userId);
        user.setUsername(username);
    }

    @Test
    void whenFindById_thenReturnUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void whenGetByUsername_thenReturnUser() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getByUsername(username);

        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void whenCreateUser_thenUserIsCreated() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals(userId, createdUser.getId());
        verify(userRepository).save(user);
    }

    @Test
    void whenChangeUserRole_thenRoleIsChanged() {
        Role newRole = Role.USER;
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changeUserRole(user, newRole);

        assertTrue(user.getRoles().contains(newRole));
        verify(userRepository).save(user);
    }
}
