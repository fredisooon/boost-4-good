package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Сущность пользователя")
@NoArgsConstructor
public class UserDto {

    @Schema(description = "Идентификатор")
    private UUID id;
    @Schema(description = "Имя")
    private String name;
    @Schema(description = "Логин/Mail")
    private String username;
    @Schema(description = "Пароль")
    private String password;
    @Schema(description = "Роль пользователя")
    private Set<Role> roles;

}
