package fyodor.dev.coremicroservice.rest.mapper;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionDefinition;
import fyodor.dev.coremicroservice.rest.dto.SubscriptionDefinitionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubscriptionDefinitionMapper {

    SubscriptionDefinitionMapper INSTANCE = Mappers.getMapper(SubscriptionDefinitionMapper.class);

    SubscriptionDefinitionDto toDto(SubscriptionDefinition subscriptionDefinition);
}
