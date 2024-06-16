package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.rest.dto.UserImageDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ImageService {

    String upload(UserImageDto image);
    ResponseEntity<InputStreamResource> getImageByUserId(UUID userId);
}

