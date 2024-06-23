package fyodor.dev.coremicroservice.rest.dto.request;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSubscriptionRequest {

    UUID creatorId;
    UUID subscriptionDefinitionId;
    SubscriptionType type;
    Integer cost;
    Integer period;
}
