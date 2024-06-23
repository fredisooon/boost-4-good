package fyodor.dev.authmicroservice.service;


import fyodor.dev.authmicroservice.rest.UserDto;
import fyodor.dev.authmicroservice.rest.dto.auth.JwtRequest;
import fyodor.dev.authmicroservice.rest.dto.auth.JwtResponse;
import fyodor.dev.authmicroservice.rest.dto.auth.RegisteredUser;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

    RegisteredUser registration(UserDto userDto);
}
