package fyodor.dev.coremicroservice.rest.dto.request;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionLevelType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSubscriptionDefinitionRequest {

    String title;
    String description;
    Integer cost;
    SubscriptionLevelType level;
}
