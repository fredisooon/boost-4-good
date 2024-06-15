package fyodor.dev.authmicroservice.rest;

import fyodor.dev.authmicroservice.domain.user.Role;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;

    private String name;

    private String username;

    private String password;

    private Set<Role> roles;
}
