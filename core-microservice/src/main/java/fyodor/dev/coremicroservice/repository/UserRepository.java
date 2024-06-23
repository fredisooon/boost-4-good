package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID userId);
    Optional<User> findByName(String name);
    Optional<User> findByUsername(String username);
}
