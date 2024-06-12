package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.domain.exception.UserAlreadyExistsException;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.rest.dto.CreateUserRequest;
import fyodor.dev.coremicroservice.rest.dto.UserDto;
import fyodor.dev.coremicroservice.rest.mapper.UserMapper;
import fyodor.dev.coremicroservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/core/user")
@Tag(name="UserController", description="Взаимодействие с аккаунтом пользователя")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создание пользователя", description = "Создает аккаунт пользователя в системе")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Parameter(description = "Сущность пользователя")
                                              @RequestBody CreateUserRequest createUserRequest) {
        try {
            User user = UserMapper.INSTANCE.toEntity(createUserRequest);
            User createdUser = userService.create(user);

            return ResponseEntity.ok().body(UserMapper.INSTANCE.toDto(createdUser));
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
