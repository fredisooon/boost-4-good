package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.feed.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Optional<Post> findById(UUID postId);
    List<Post> findBySubscriptionId(UUID subscriptionId);


    @Query("SELECT p FROM Post p WHERE p.subscription.id = :subId")
    Optional<List<Post>> findAllBySubscriptionId(@Param("subId") UUID subId);

    List<Post> findTopNByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.subscription s WHERE s.subscriber.id = :userId")
    List<Post> findAllByUserIdWithLimit(@Param("userId") UUID userId, Pageable pageable);


//    List<Post> findAllWithLimit(Pageable pageable);

//    @Query("SELECT p FROM Post p JOIN p.subscription s WHERE s.subscriber.id = :userId AND s.allowed = true")
//    List<Post> findAllowedPostsByUserId(@Param("userId") UUID userId, Pageable pageable);
//
}
