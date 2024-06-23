package fyodor.dev.coremicroservice.rest.mapper;

import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.rest.dto.SubscriptionDto;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateSubscriptionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionDto toDto(Subscription subscription);

    Subscription toEntity(CreateSubscriptionRequest request);

    void updateEntity(UpdateSubscriptionRequest request, @MappingTarget Subscription subscription);
}