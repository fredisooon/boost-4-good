package fyodor.dev.coremicroservice.repository;

import fyodor.dev.coremicroservice.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    List<Image> findAllById(Iterable<UUID> ids);

    @Query("SELECT u.image FROM User u WHERE u.id = :userId")
    Optional<Image> findByUserId(@Param("userId") UUID userId);

}
