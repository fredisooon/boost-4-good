package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import lombok.Data;

@Data
public class UpdateReactionRequest {
    private ReactionType type;
}
