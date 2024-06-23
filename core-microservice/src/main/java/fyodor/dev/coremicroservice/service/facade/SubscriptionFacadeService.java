package fyodor.dev.coremicroservice.service.facade;

import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.exception.UnauthorizedException;
import fyodor.dev.coremicroservice.domain.feed.SubscriptionType;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.SubscriptionRepository;
import fyodor.dev.coremicroservice.repository.UserRepository;
import fyodor.dev.coremicroservice.rest.dto.FeedDataDto;
import fyodor.dev.coremicroservice.rest.dto.PostDto;
import fyodor.dev.coremicroservice.rest.dto.SubscriptionDto;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.mapper.PostMapper;
import fyodor.dev.coremicroservice.rest.mapper.SubscriptionMapper;
import fyodor.dev.coremicroservice.service.PostService;
import fyodor.dev.coremicroservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionFacadeService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final PostMapper postMapper;
    private final SubscriptionService subscriptionService;
    private final PostService postService;

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

    @Transactional(readOnly = true)
    public FeedDataDto getFeed(UUID userId, Integer limit, Integer commentsLimit, Boolean onlyAllowed) {
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberId(userId);
        List<Post> feedPosts = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            UUID subId = subscription.getId();
            List<Post> postsBySubscription = postService.getPostsBySubscription(subId);
            if (onlyAllowed) {
                postsBySubscription = postsBySubscription.stream()
                        .filter(post -> subscription.getType() == SubscriptionType.TRACKING ||
                                post.getSubscription().getSubscriptionDefinition().getLevel().ordinal() <=
                                        subscription.getSubscriptionDefinition().getLevel().ordinal())
                        .toList();
            }
            feedPosts.addAll(postsBySubscription);
        }
        feedPosts = feedPosts.stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .limit(limit)
                .collect(Collectors.toList());

        List<PostDto> postDtos = feedPosts.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());

        List<SubscriptionDto> subscriptionDtos = subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());

        FeedDataDto feedDataDto = new FeedDataDto();
        feedDataDto.setPosts(postDtos);
        feedDataDto.setBlogs(subscriptionDtos);
        return feedDataDto;
    }
}
