package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.feed.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    List<Subscription> findAllBySubscriberUsername(String username);
    List<Subscription> findAllBySubscriberId(UUID id);
    Subscription findByCreatorIdAndSubscriberId(UUID creatorId, UUID readerId);

}
