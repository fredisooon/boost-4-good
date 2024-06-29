package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.chat.Chat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatService {

    Chat save(Chat chat);
    Optional<Chat> findById(UUID chatId);
    List<Chat> findAll();
}
