package fyodor.dev.coremicroservice.rest.mapper;

import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.rest.dto.SubscriptionDto;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateSubscriptionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "subscriber.id", target = "subscriberId")
    @Mapping(source = "subscriptionDefinition.id", target = "subscriptionDefinitionId")
    SubscriptionDto toDto(Subscription subscription);

    @Mapping(target = "creator.id", source = "creatorId")
    @Mapping(target = "subscriber.id", source = "subscriberId")
    @Mapping(target = "subscriptionDefinition.id", source = "subscriptionDefinitionId")
    Subscription toEntity(CreateSubscriptionRequest request);

    @Mapping(target = "creator.id", source = "creatorId")
    @Mapping(target = "subscriber.id", source = "subscriberId")
    @Mapping(target = "subscriptionDefinition.id", source = "subscriptionDefinitionId")
    void updateEntity(UpdateSubscriptionRequest request, @MappingTarget Subscription subscription);
}