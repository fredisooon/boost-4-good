package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import lombok.Data;

@Data
public class ReactionSummaryDto {

    private ReactionType reactionType;
    private long count;
}
