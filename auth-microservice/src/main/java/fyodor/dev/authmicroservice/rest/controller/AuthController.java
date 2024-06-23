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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="AuthController", description="Авторизация и аутентификация пользователей в системе")
@Validated
@RestController
@RequestMapping("${api.version}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Логин пользователя", description = "Авторизация пользователя в системе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    @PostMapping("/login")
    public JwtResponse login(@Parameter(description = "Логин и пароль для входа")
                                 @RequestBody @Valid final  JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "Регистрация пользователя", description = "Регистрация нового пользователя в системе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                    content = @Content(schema = @Schema(implementation = RegisteredUser.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка при регистрации")
    })
    @PostMapping("/register")
    public RegisteredUser registration(@Parameter(description = "Данные пользователя для регистрации")
                                           @RequestBody @Valid final UserDto userDto) {
        return authService.registration(userDto);
    }

    @Operation(summary = "Обновление токена", description = "Обновление JWT токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление токена",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "403", description = "Невалидный рефреш токен")
    })
    @PostMapping("/refresh")
    public JwtResponse refresh(@Parameter(description = "Рефреш токен")@RequestBody @Valid final String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
