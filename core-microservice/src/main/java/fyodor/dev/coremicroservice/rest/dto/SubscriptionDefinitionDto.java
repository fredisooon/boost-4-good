package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionLevelType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionDefinitionDto {

    UUID id;
    String title;
    String description;
    Integer cost;
    SubscriptionLevelType level;
}
