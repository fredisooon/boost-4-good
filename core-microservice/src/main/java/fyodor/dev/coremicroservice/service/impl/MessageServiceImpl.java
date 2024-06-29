package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.chat.Message;
import fyodor.dev.coremicroservice.repository.MessageRepository;
import fyodor.dev.coremicroservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> findById(UUID messageId) {
        return messageRepository.findById(messageId);
    }
}
