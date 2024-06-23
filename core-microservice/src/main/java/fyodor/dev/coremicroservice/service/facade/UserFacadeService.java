package fyodor.dev.coremicroservice.service.facade;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.user.Role;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.UserRepository;
import fyodor.dev.coremicroservice.rest.dto.CreateUserRequest;
import fyodor.dev.coremicroservice.rest.dto.JwtRequest;
import fyodor.dev.coremicroservice.rest.dto.UserDto;
import fyodor.dev.coremicroservice.rest.mapper.UserMapper;
import fyodor.dev.coremicroservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacadeService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserDto createUser(CreateUserRequest createUserRequest) {
        User user = userMapper.toEntity(createUserRequest);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    public UserDto getUserByJwtRequest(JwtRequest jwtRequest) {
        User user = userService.getByUsername(jwtRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public UserDto getUserByUsername(String username) {
        User user = userService.getByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public void changeUserRole(UUID userId, Role newRole) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userService.changeUserRole(user, newRole);
    }
}
