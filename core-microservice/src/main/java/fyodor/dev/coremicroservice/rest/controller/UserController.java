package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.exception.UserAlreadyExistsException;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.rest.dto.CreateUserRequest;
import fyodor.dev.coremicroservice.rest.dto.JwtRequest;
import fyodor.dev.coremicroservice.rest.dto.UserDto;
import fyodor.dev.coremicroservice.rest.dto.UserImageDto;
import fyodor.dev.coremicroservice.rest.mapper.UserMapper;
import fyodor.dev.coremicroservice.service.ImageService;
import fyodor.dev.coremicroservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/core/user")
@Tag(name="UserController", description="Взаимодействие с аккаунтом пользователя")
public class UserController { // TODO [15.06.2024]: swagger actualization

    private final UserService userService;
    private final UserMapper userMapper;
    private final ImageService imageService;

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

    @Operation(summary = "Получение пользователя???", description = "Получает сущность пользователя???")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserDto> getUser(@Parameter(description = "123") @RequestBody JwtRequest jwtRequest) {
        try {
            User user = userService.getByUsername(jwtRequest.getUsername());
            return ResponseEntity.ok().body(userMapper.toDto(user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserByUsername(@Parameter(description = "username/e-mail")
                                                         @RequestParam(value = "username") String username) {
        try {
            User user = userService.getByUsername(username);
            return ResponseEntity.ok().body(userMapper.toDto(user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/images")
    @Operation(summary = "Загрузка картинки пользователя", description = "Загрузка картикни пользователя в профиль")
    public ResponseEntity<Void> uploadImage(@PathVariable UUID userId, @ModelAttribute UserImageDto userImageDto) {
        try {
            userService.uploadImage(userId, userImageDto);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/images")
    @Operation(summary = "Получение картинки пользователя", description = "Получение картинки пользователя из профиля")
    public ResponseEntity<InputStreamResource> getUserImage(@PathVariable UUID userId) {
        return imageService.getImageByUserId(userId);
//        try {
//            InputStreamResource imageByUserId = imageService.getImageByUserId(userId);

//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);

//            return imageService.getImageByUserId(userId);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
    }
}
