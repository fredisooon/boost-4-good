package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.domain.feed.SubscriptionDefinition;
import fyodor.dev.coremicroservice.domain.feed.SubscriptionType;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.SubscriptionDefinitionRepository;
import fyodor.dev.coremicroservice.repository.SubscriptionRepository;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateSubscriptionRequest;
import fyodor.dev.coremicroservice.service.SubscriptionService;
import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionDefinitionRepository subscriptionDefinitionRepository;

    @Transactional
    @Override
    public Subscription createSubscription(User subscriber, User creator, CreateSubscriptionRequest request) {
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setCreator(creator);
        subscription.setType(request.getType());
        subscription.setStartDate(LocalDateTime.now());

        if (request.getType() == SubscriptionType.TRACKING) {
            subscription.setCost(0);
            subscription.setPeriod(null);
            subscription.setEndDate(null);
            subscription.setSubscriptionDefinition(null);
        } else {
            SubscriptionDefinition subscriptionDefinition = subscriptionDefinitionRepository.findById(request.getSubscriptionDefinitionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subscription definition not found"));
            subscription.setSubscriptionDefinition(subscriptionDefinition);
            subscription.setCost(request.getCost());
            subscription.setPeriod(request.getPeriod());
            subscription.setEndDate(LocalDateTime.now().plusMonths(request.getPeriod()));
        }

        return subscriptionRepository.save(subscription);
    }

    @Transactional
    @Override
    public void updateSubscription(Subscription subscription, UpdateSubscriptionRequest request) {
        if (request.getType() == SubscriptionType.TRACKING) {
            subscription.setCost(0);
            subscription.setPeriod(null);
            subscription.setEndDate(null);
            subscription.setSubscriptionDefinition(null);
            subscription.setType(SubscriptionType.TRACKING);
        } else {
            SubscriptionDefinition subscriptionDefinition = subscriptionDefinitionRepository.findById(request.getSubscriptionDefinitionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subscription definition not found"));
            subscription.setSubscriptionDefinition(subscriptionDefinition);
            subscription.setCost(request.getCost());
            subscription.setPeriod(request.getPeriod());
            subscription.setEndDate(subscription.getStartDate().plusMonths(request.getPeriod()));
            subscription.setType(SubscriptionType.STANDARD);
        }
    }
}
