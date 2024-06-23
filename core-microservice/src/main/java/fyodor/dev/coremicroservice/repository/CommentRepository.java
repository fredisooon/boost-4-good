package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.feed.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByPostId(UUID postId);
}
