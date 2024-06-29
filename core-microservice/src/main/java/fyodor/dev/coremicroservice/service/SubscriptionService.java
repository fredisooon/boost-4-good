package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateSubscriptionRequest;

import java.util.UUID;

public interface SubscriptionService {

    Subscription createSubscription(User subscriber, User creator, CreateSubscriptionRequest request);
    void updateSubscription(Subscription subscription, UpdateSubscriptionRequest request);
    boolean hasValidSubscription(UUID creatorId, UUID readerId);
}
