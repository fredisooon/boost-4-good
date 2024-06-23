package fyodor.dev.coremicroservice.service.facade;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.feed.SubscriptionDefinition;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.UserRepository;
import fyodor.dev.coremicroservice.rest.dto.SubscriptionDefinitionDto;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionDefinitionRequest;
import fyodor.dev.coremicroservice.rest.mapper.SubscriptionDefinitionMapper;
import fyodor.dev.coremicroservice.service.SubscriptionDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionDefinitionFacadeService {

    private final UserRepository userRepository;
    private final SubscriptionDefinitionService subscriptionDefinitionService;
    private final SubscriptionDefinitionMapper subscriptionDefinitionMapper;


    public SubscriptionDefinitionDto createSubscriptionDefinition(UUID creatorId,
                                                                  CreateSubscriptionDefinitionRequest request) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        SubscriptionDefinition subscriptionDefinition = subscriptionDefinitionService.createSubscriptionDefinition(creator, request);
        return subscriptionDefinitionMapper.toDto(subscriptionDefinition);
    }
}
