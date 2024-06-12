package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.user.Role;
import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {

    private String name;
    private String username;
    private String password;
    private Set<Role> roles;
}
