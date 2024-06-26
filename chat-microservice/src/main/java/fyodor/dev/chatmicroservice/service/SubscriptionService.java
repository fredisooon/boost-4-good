package fyodor.dev.chatmicroservice.service;

import java.util.UUID;

public interface SubscriptionService {

    boolean hasValidSubscription(UUID creatorId, UUID readerId);

}
