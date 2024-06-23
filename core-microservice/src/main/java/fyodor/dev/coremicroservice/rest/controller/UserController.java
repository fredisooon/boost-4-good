package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.domain.user.Role;
import fyodor.dev.coremicroservice.rest.dto.CreateUserRequest;
import fyodor.dev.coremicroservice.rest.dto.JwtRequest;
import fyodor.dev.coremicroservice.rest.dto.UserDto;
import fyodor.dev.coremicroservice.service.facade.UserFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/core/crud/user")
@Tag(name="UserController", description="Взаимодействие с аккаунтом пользователя")
public class UserController {

    private final UserFacadeService userFacadeService;

    @PostMapping
    @Operation(summary = "Создание пользователя", description = "Создает аккаунт пользователя в системе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Аккаунт создан",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка при создании")
    })
    public ResponseEntity<UserDto> createUser(@Parameter(description = "Сущность пользователя")
                                                @RequestBody CreateUserRequest createUserRequest) {
        UserDto createdUser = userFacadeService.createUser(createUserRequest);
        log.info("User created successfully with username: {}", createdUser.getUsername());
        return ResponseEntity.ok().body(createdUser);
    }

    @PostMapping(value = "/validate")
    @Operation(summary = "Валидация пользователя при логине",
            description = "Получение сущности пользователя из БД по его логину (username)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная валидация",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка при валидации")
    })
    public ResponseEntity<UserDto> getUser(@Parameter(description = "Запрос с данным для валидации")
                                               @RequestBody JwtRequest jwtRequest) {
        log.info("Request to validate user with username: {}", jwtRequest.getUsername());
        UserDto user = userFacadeService.getUserByJwtRequest(jwtRequest);
        log.info("User validation successful for username: {}", jwtRequest.getUsername());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    @Operation(summary = "Получение пользователя по его логину",
            description = "Получение сущности пользователя из БД по его логину (username)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сущность пользователя",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка при получении")
    })
    public ResponseEntity<UserDto> getUserByUsername(@Parameter(description = "username/e-mail")
                                                     @RequestParam(value = "username") String username) {
        log.info("Request to get user by username: {}", username);
        UserDto user = userFacadeService.getUserByUsername(username);
        log.info("User retrieved successfully for username: {}", username);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{userId}/role")
    @Operation(summary = "Смена роли пользователя",
            description = "Смена роли пользователя на новую")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пусто",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка при получении")
    })
    public ResponseEntity<Void> changeUserRole(@Parameter(description = "UUID пользователя")
                                                   @PathVariable UUID userId,
                                               @Parameter(description = "новая роль пользователя")
                                                    @RequestParam Role newRole) {
        userFacadeService.changeUserRole(userId, newRole);
        return ResponseEntity.noContent().build();
    }
}
