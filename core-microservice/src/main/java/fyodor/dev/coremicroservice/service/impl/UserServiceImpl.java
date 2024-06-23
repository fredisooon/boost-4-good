package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.exception.UserAlreadyExistsException;
import fyodor.dev.coremicroservice.domain.user.Image;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.ImageRepository;
import fyodor.dev.coremicroservice.repository.UserRepository;
import fyodor.dev.coremicroservice.rest.dto.UserImageDto;
import fyodor.dev.coremicroservice.service.ImageService;
import fyodor.dev.coremicroservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @Override
    public User getById(final UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("User with id '%s' not found", id)));
    }

    @Override
    public User create(final User user) {
        Optional<User> existUser = userRepository.findByUsername(user.getUsername());
        if (!existUser.isPresent()) {
            return userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException(format("User with username '%s' already exists", user.getUsername()));
        }
    }

    @Override
    public User getByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(format("User with username '%s' not found", username)));
    }

    @Override
    public User update(final User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(final UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional()
    public void uploadImage(UUID userId, UserImageDto imageDto) {
        String minioLink = imageService.upload(imageDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(format("User with id '%s' not found", userId)));

        Image image = new Image();
        image.setImageLink(minioLink);
        image.setUserId(user);
        imageRepository.save(image);

        user.setImage(image);
        userRepository.save(user);
    }
}
