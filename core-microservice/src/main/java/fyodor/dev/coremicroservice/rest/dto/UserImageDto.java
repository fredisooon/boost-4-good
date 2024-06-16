package fyodor.dev.coremicroservice.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserImageDto {

    @NotNull(message = "Image must be not null.")
    private MultipartFile file;
}
