package fyodor.dev.coremicroservice.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePostRequest {

    String title;
    String content;
    List<UUID> imageIds;
    Integer commentCount;
    Integer likeCount;
    Integer dislikeCount;
}