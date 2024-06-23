package fyodor.dev.coremicroservice.rest.dto.request;

import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import lombok.Data;

import java.util.UUID;

@Data
public class ReactionRequest {

    private UUID postId;
    private ReactionType type;
}
