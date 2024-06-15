package fyodor.dev.authmicroservice.rest.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisteredUser {

    private UUID id;
    private String name;
    private String username;
}
