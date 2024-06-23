package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.feed.Reaction;
import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, UUID> {

    Optional<Reaction> findByPostIdAndUserUsername(UUID postId, String username);
    Optional<Reaction> findByPostIdAndType(UUID postId, ReactionType type);
    List<Reaction> findByPostId(UUID postId);
}
