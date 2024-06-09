package ru.summer.workshop.tdbtitle.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.summer.workshop.tdbtitle.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController { // TODO [09.06.2024]: заглушка для проверка branch policy. доделать

    private AuthService authService;

}
