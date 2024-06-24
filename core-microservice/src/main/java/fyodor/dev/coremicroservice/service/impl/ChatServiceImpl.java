package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.chat.Chat;
import fyodor.dev.coremicroservice.repository.ChatRepository;
import fyodor.dev.coremicroservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public Optional<Chat> findById(UUID chatId) {
        return chatRepository.findById(chatId);
    }

    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }
}
