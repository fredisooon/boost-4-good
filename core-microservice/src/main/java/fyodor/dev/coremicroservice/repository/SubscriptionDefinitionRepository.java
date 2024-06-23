package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.feed.SubscriptionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionDefinitionRepository extends JpaRepository<SubscriptionDefinition, UUID> {
}
