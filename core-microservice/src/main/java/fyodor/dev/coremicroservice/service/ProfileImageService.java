package fyodor.dev.coremicroservice.service;

import org.apache.commons.lang3.tuple.Pair;
import fyodor.dev.coremicroservice.rest.dto.UserImageDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

public interface ProfileImageService {

    Pair<InputStreamResource, HttpHeaders> getProfileImage(UUID userId);
    void uploadProfileImage(UUID userId, UserImageDto dto);
    void clearProfileImage(UUID userId);

}

