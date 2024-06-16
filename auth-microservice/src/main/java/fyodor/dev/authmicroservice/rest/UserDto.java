package fyodor.dev.authmicroservice.rest;

import fyodor.dev.authmicroservice.domain.user.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {

    @ApiModelProperty(hidden = true)
    private UUID id;

    private String name;

    private String username;

    private String password;

    @ApiModelProperty(hidden = true)
    private Set<Role> roles;
}
