package fyodor.dev.coremicroservice.rest.dto.request;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSubscriptionRequest {

    private UUID subscriptionDefinitionId;
    private SubscriptionType type;
    private Integer cost;
    private Integer period;
}
