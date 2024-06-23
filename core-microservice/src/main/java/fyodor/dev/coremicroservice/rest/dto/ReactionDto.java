package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactionDto {

    UUID id;
    UUID postId;
    UUID userId;
    ReactionType type;
}
