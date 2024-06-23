package fyodor.dev.coremicroservice.service.impl.image;

import fyodor.dev.coremicroservice.domain.exception.ImageAlreadyExistsException;
import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.image.Image;
import fyodor.dev.coremicroservice.domain.image.ImageType;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.ImageRepository;
import fyodor.dev.coremicroservice.repository.UserRepository;
import fyodor.dev.coremicroservice.rest.dto.UserImageDto;
import fyodor.dev.coremicroservice.service.AbstractImageService;
import fyodor.dev.coremicroservice.service.ProfileImageService;
import fyodor.dev.coremicroservice.service.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static fyodor.dev.coremicroservice.util.MinioUtil.determineMediaType;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileImageServiceImpl extends AbstractImageService implements ProfileImageService {

    @Getter
    private final MinioClient minioClient;
    @Getter
    private final MinioProperties minioProperties;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;


    @Override
    public Pair<InputStreamResource, HttpHeaders> getProfileImage(UUID userId) {
        Image image = imageRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found for user with ID: " + userId));

        String imageLink = image.getMinioLink();
        MediaType mediaType = determineMediaType(imageLink);

        InputStreamResource imageResource = getImage(imageLink);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        log.info("Image retrieved successfully for user with ID: {}", userId);
        return Pair.of(imageResource, headers);
    }

    @Override
    @Transactional
    public void uploadProfileImage(UUID userId, UserImageDto imageDto) {
        Image existingImage = imageRepository.findByUserId(userId).orElse(null);
        if (existingImage != null) {
            throw new ImageAlreadyExistsException("Image already exists for user with ID: " + userId);
        }

        String minioImageLink = upload(imageDto.getFile());
        Image image = new Image();
        image.setMinioLink(minioImageLink);
        image.setType(ImageType.PROFILE_IMAGE);
        imageRepository.save(image);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id '%s' not found".formatted(userId)));

        user.setImage(image);
        userRepository.save(user);

        log.info("Profile image uploaded: {}", image);
    }

    @Override
    @Transactional
    public void clearProfileImage(UUID userId) {
        Image image = imageRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found for user with ID: " + userId));

        deleteFromS3(image.getMinioLink());
        imageRepository.delete(image);

        log.info("Profile image deleted for user with ID: {}", userId);
    }
}
