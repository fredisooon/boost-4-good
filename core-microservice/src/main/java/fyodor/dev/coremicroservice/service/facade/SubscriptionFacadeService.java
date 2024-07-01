package fyodor.dev.coremicroservice.service.facade;

import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.exception.UnauthorizedException;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.SubscriptionRepository;
import fyodor.dev.coremicroservice.repository.UserRepository;
import fyodor.dev.coremicroservice.rest.dto.SubscriptionDto;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.mapper.PostMapper;
import fyodor.dev.coremicroservice.rest.mapper.SubscriptionMapper;
import fyodor.dev.coremicroservice.service.PostService;
import fyodor.dev.coremicroservice.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionFacadeService {

    SubscriptionRepository subscriptionRepository;
    UserRepository userRepository;
    SubscriptionMapper subscriptionMapper;
    SubscriptionService subscriptionService;

    public boolean hasValidSubscription(UUID creatorId, UUID readerId) {
        return subscriptionService.hasValidSubscription(creatorId, readerId);
    }

    @Transactional
    public SubscriptionDto createSubscription(String userLogin, CreateSubscriptionRequest createSubscriptionRequest) {
        log.info("Creating subscription for user ID: {}", userLogin);
        User creator = userRepository.findById(createSubscriptionRequest.getCreatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found"));
        User subscriber = userRepository.findByUsername(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("Subscriber not found"));
        Subscription subscription = subscriptionService.createSubscription(subscriber, creator, createSubscriptionRequest);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        log.info("Subscription created with ID: {}", savedSubscription.getId());
        return subscriptionMapper.toDto(savedSubscription);
    }

    @Transactional(readOnly = true)
    public SubscriptionDto getSubscription(UUID subscriptionId) {
        log.info("Fetching subscription with ID: {}", subscriptionId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        return subscriptionMapper.toDto(subscription);
    }

    @Transactional
    public SubscriptionDto updateSubscription(String userLogin, UUID subscriptionId, UpdateSubscriptionRequest updateSubscriptionRequest) {
        log.info("Updating subscription with ID: {}", subscriptionId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        if (!subscription.getSubscriber().getUsername().equals(userLogin)) {
            log.error("User not authorized to update subscription with ID: {}", subscriptionId);
            throw new UnauthorizedException("User not authorized to update this subscription");
        }
        subscriptionService.updateSubscription(subscription, updateSubscriptionRequest);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        log.info("Subscription updated with ID: {}", updatedSubscription.getId());
        return subscriptionMapper.toDto(updatedSubscription);
    }

    @Transactional
    public void deleteSubscription(String userLogin, UUID subscriptionId) {
        log.info("Deleting subscription with ID: {}", subscriptionId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        if (!subscription.getSubscriber().getUsername().equals(userLogin)) {
            log.error("User not authorized to delete subscription with ID: {}", subscriptionId);
            throw new UnauthorizedException("User not authorized to delete this subscription");
        }
        subscriptionRepository.delete(subscription);
        log.info("Subscription deleted with ID: {}", subscriptionId);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionDto> getSubscriptionsByUser(UUID userId) {
        log.info("Fetching subscriptions for user ID: {}", userId);
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberId(userId);
        return subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }
}
