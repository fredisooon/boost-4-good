package fyodor.dev.chatmicroservice.service.impl;

import fyodor.dev.chatmicroservice.rest.dto.ChatDto;
import fyodor.dev.chatmicroservice.rest.dto.MessageDto;
import fyodor.dev.chatmicroservice.service.ChatFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatFacadeServiceImpl implements ChatFacadeService {

    private final RestTemplate restTemplate;

    private final String crudMicroserviceUrl = "http://localhost:8003";

    @Override
    public ChatDto startChat(UUID creatorId, UUID readerId) {
        ChatDto chatDto = new ChatDto();
        chatDto.setCreatorId(creatorId);
        chatDto.setReaderId(readerId);

        return restTemplate.postForObject(crudMicroserviceUrl + "/api/v1/core/crud/chats", chatDto, ChatDto.class);
    }

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        messageDto.setTimestamp(LocalDateTime.now());
        return restTemplate.postForObject(crudMicroserviceUrl + "/api/v1/core/crud/messages", messageDto, MessageDto.class);
    }
}
