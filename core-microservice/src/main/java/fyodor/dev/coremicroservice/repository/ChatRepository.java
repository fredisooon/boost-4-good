package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    Optional<Chat> findByCreatorIdAndReaderId(UUID creatorId, UUID readerId);
}
