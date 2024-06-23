package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.user.Role;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class CreateUserRequest {

    private UUID id;
    private String name;
    private String username;
    private String password;
    private Set<Role> roles;
}
