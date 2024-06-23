package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.user.Role;
import fyodor.dev.coremicroservice.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(UUID userId);
    Optional<User> getByUsername(String username);
    User create(User user);
    void changeUserRole(User user, Role newRole);
}
