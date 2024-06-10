package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.user.User;

import java.util.UUID;

public interface UserService {

    User getById(UUID id);
    User getByUsername(String username);
    User create(User user);
    User update(User user);
    void delete(UUID id);
}
