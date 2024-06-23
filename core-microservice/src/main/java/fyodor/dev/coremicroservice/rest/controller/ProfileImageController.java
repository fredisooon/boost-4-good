package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.rest.dto.UserImageDto;
import fyodor.dev.coremicroservice.service.ProfileImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/core/crud/profile/image")
@Tag(name="ProfileImageController", description="Взаимодействие с картинкой пользователя")
public class ProfileImageController {

    private final ProfileImageService imageService;

    @PostMapping
    @Operation(summary = "Загрузка картинки пользователя", description = "Загрузка картикни пользователя в профиль")
    public ResponseEntity<Void> uploadImage(@Parameter(description = "UUID пользователя")
                                                @RequestParam UUID userId,
                                            @Parameter(description = "Файл с картинкой")
                                            @ModelAttribute UserImageDto userImageDto) {
        log.info("Request to upload image for user with ID: {}", userId);

        imageService.uploadProfileImage(userId, userImageDto);

        log.info("Image uploaded successfully for user with ID: {}", userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Получение картинки пользователя", description = "Получение картинки пользователя из профиля")
    public ResponseEntity<InputStreamResource> getUserImage(@Parameter(description = "UUID пользователя")
                                                                @RequestParam UUID userId) {
        log.info("Request to get image for user with ID: {}", userId);

        Pair<InputStreamResource, HttpHeaders> responsePair = imageService.getProfileImage(userId);

        log.info("Image retrieved successfully for user with ID: {}", userId);
        return ResponseEntity.ok().headers(responsePair.getRight()).body(responsePair.getLeft());
    }

    @DeleteMapping
    @Operation(summary = "Удаление картинки пользователя", description = "Удаление картинки пользователя из профиля")
    public ResponseEntity<Void> deleteUserImage(@Parameter(description = "UUID пользователя")
                                                    @RequestParam UUID userId) {
        log.info("Request to delete image for user with ID: {}", userId);

        imageService.clearProfileImage(userId);

        log.info("Image deleted successfully for user with ID: {}", userId);
        return ResponseEntity.ok().build();
    }

}
