package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.user.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    @Query("SELECT i FROM Image i WHERE i.userId.id = :userId")
    Optional<Image> findByUserId(@Param("userId") UUID userId);   // ???
}
