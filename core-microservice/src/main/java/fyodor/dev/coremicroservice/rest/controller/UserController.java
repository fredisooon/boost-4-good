package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {  // TODO [11.06.2024]:  версионировние

    private final UserService userService;

}
