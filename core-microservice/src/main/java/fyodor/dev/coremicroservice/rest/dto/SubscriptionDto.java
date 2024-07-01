package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionDto {

    UUID id;
    UUID creatorId;
    String creatorName;
    UUID subscriberId;
    UUID subscriptionDefinitionId;
    SubscriptionType type;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer cost;
    Integer period;
}
