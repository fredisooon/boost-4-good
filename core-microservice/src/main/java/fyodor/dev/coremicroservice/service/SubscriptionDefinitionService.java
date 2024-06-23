package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionDefinition;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionDefinitionRequest;

public interface SubscriptionDefinitionService {

    SubscriptionDefinition createSubscriptionDefinition(User creator, CreateSubscriptionDefinitionRequest request);
}
