package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.chat.Message;

import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    Message saveMessage(Message message);
    Optional<Message> findById(UUID messageId);
}
