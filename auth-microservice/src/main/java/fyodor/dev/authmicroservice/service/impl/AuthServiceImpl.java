package fyodor.dev.authmicroservice.service.impl;

import fyodor.dev.authmicroservice.domain.exception.AccessDeniedException;
import fyodor.dev.authmicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.authmicroservice.domain.user.Role;
import fyodor.dev.authmicroservice.rest.UserDto;
import fyodor.dev.authmicroservice.rest.dto.auth.JwtRequest;
import fyodor.dev.authmicroservice.rest.dto.auth.JwtResponse;
import fyodor.dev.authmicroservice.rest.dto.auth.RegisteredUser;
import fyodor.dev.authmicroservice.rest.security.JwtTokenProvider;
import fyodor.dev.authmicroservice.service.AuthService;
import fyodor.dev.authmicroservice.service.properties.LinkProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final LinkProperties linkProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;


    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                        )
        );

        HttpEntity<JwtRequest> request = new HttpEntity<>(loginRequest);
        ResponseEntity<UserDto> response = restTemplate
                .exchange(linkProperties.getUserServiceLink() + "/validate",
                        HttpMethod.POST,
                        request,
                        UserDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            UserDto userDto = response.getBody();
            jwtResponse.setId(userDto.getId());
            jwtResponse.setUsername(userDto.getUsername());
            jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(userDto.getId(), userDto.getUsername(), userDto.getRoles()));
            jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(userDto.getId(), userDto.getUsername()));

            return jwtResponse;
        } else {
            throw new AccessDeniedException("Неправильный логин или пароль");
        }
    }

    @Override
    public RegisteredUser registration(final UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(new HashSet<>(Collections.singleton(Role.ROLE_USER)));

        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        ResponseEntity<UserDto> response = restTemplate.exchange(linkProperties.getUserServiceLink(),
                                                                                HttpMethod.POST,
                                                                                request,
                                                                                UserDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResourceNotFoundException("Не удалось зарегистрироваться");
        }

        UserDto user = response.getBody();
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername(user.getUsername());
        registeredUser.setId(user.getId());
        registeredUser.setName(user.getName());

        return registeredUser;
    }

    @Override
    public JwtResponse refresh(final String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
