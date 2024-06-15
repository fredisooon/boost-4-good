package fyodor.dev.coremicroservice.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {

    @NotNull(message = "Username must not be null.")
    private String username;
    @NotNull(message = "Password must not be null.")
    private String password;

}
