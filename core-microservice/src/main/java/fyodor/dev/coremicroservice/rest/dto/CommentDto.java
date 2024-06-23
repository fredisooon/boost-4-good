package fyodor.dev.coremicroservice.rest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

     UUID id;
     UUID postId;
     UUID userId;
     String content;
     LocalDateTime createdAt;
}
