package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.rest.dto.UserImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {

    User getById(UUID id);
    User getByUsername(String username);
    User create(User user);
    User update(User user);
    void uploadImage(UUID id, UserImageDto imageDto);
    void delete(UUID id);
}
