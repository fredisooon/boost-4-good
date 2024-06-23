package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionDefinition;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.SubscriptionDefinitionRepository;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionDefinitionRequest;
import fyodor.dev.coremicroservice.service.SubscriptionDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionDefinitionServiceImpl implements SubscriptionDefinitionService {

    private final SubscriptionDefinitionRepository subscriptionDefinitionRepository;

    @Override
    public SubscriptionDefinition createSubscriptionDefinition(User creator, CreateSubscriptionDefinitionRequest request) {
        SubscriptionDefinition subscriptionDefinition = new SubscriptionDefinition();
        subscriptionDefinition.setTitle(request.getTitle());
        subscriptionDefinition.setDescription(request.getDescription());
        subscriptionDefinition.setCost(request.getCost());
        subscriptionDefinition.setLevel(request.getLevel());

        return subscriptionDefinitionRepository.save(subscriptionDefinition);
    }
}
