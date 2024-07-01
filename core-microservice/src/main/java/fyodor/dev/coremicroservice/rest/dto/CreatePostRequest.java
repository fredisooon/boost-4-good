package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.image.Image;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePostRequest {

    String title;
    String content;
    UUID subscriptionId;
    UUID creatorId;
    List<UUID> imageIds;
    Integer commentCount;
    Integer likeCount;
    Integer dislikeCount;
}
