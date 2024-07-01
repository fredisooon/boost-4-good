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
    UUID creatorId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Integer commentCount;
    Integer likeCount;
    Integer dislikeCount;
    List<ImageDto> images;
}
