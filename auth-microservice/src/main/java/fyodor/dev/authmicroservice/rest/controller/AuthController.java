package fyodor.dev.authmicroservice.rest.controller;

import fyodor.dev.authmicroservice.rest.UserDto;
import fyodor.dev.authmicroservice.rest.dto.JwtRequest;
import fyodor.dev.authmicroservice.rest.dto.JwtResponse;
import fyodor.dev.authmicroservice.rest.dto.RegisteredUser;
import fyodor.dev.authmicroservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.version}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody final JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public RegisteredUser registration(@RequestBody final UserDto userDto) {
        return authService.registration(userDto);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody final String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
