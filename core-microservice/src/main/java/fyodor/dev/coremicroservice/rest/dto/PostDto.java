package fyodor.dev.coremicroservice.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {

    UUID id;
    String title;
    String content;
    UUID subscriptionId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<ImageDto> images;
}
