package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.image.ImageType;
import lombok.Data;

import java.util.UUID;

@Data
public class ImageDto {

    private UUID id;
    private String minioLink;
    private ImageType type;
}
