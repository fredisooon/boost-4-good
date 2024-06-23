package fyodor.dev.authmicroservice.rest.controller;

import fyodor.dev.authmicroservice.rest.UserDto;
import fyodor.dev.authmicroservice.rest.dto.auth.JwtRequest;
import fyodor.dev.authmicroservice.rest.dto.auth.JwtResponse;
import fyodor.dev.authmicroservice.rest.dto.auth.RegisteredUser;
import fyodor.dev.authmicroservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/auth")
@Tag(name="AuthController", description="Авторизация и аутентификация пользователей в системе")

public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Логин пользователя", description = "Авторизация пользователя в системе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    public JwtResponse login(@Parameter(description = "Логин и пароль для входа")
                                 @RequestBody @Valid final  JwtRequest loginRequest) {
        log.info("A user authorization request has been received: {}", loginRequest.getUsername());
        JwtResponse login = authService.login(loginRequest);
        log.info("Successful user authorization: {}", loginRequest.getUsername());
        return login;
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя", description = "Регистрация нового пользователя в системе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                    content = @Content(schema = @Schema(implementation = RegisteredUser.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка при регистрации")
    })
    public RegisteredUser registration(@Parameter(description = "Данные пользователя для регистрации")
                                           @RequestBody @Valid final UserDto userDto) {
        log.info("A user registration request has been received: {}", userDto.getUsername());
        RegisteredUser registeredUser = authService.registration(userDto);
        log.info("Successful user registration: {}", userDto.getUsername());
        return registeredUser;
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновление токена", description = "Обновление JWT токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление токена",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "403", description = "Невалидный рефреш токен")
    })
    public JwtResponse refresh(@Parameter(description = "Рефреш токен")@RequestBody @Valid final String refreshToken) {
        log.info("A request to refresh the token has been received");
        JwtResponse response = authService.refresh(refreshToken);
        log.info("Successful token refreshing");
        return response;
    }
}