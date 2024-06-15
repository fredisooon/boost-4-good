package fyodor.dev.authmicroservice.service;


import fyodor.dev.authmicroservice.rest.UserDto;
import fyodor.dev.authmicroservice.rest.dto.JwtRequest;
import fyodor.dev.authmicroservice.rest.dto.JwtResponse;
import fyodor.dev.authmicroservice.rest.dto.RegisteredUser;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

    RegisteredUser registration(UserDto userDto);
}
