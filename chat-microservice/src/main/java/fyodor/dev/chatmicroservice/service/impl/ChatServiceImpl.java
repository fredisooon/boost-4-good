package fyodor.dev.chatmicroservice.service.impl;

import fyodor.dev.chatmicroservice.service.ChatService;
import fyodor.dev.chatmicroservice.ws.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RestTemplate restTemplate;

    @Value("${crud.microservice.url}")
    private String crudMicroserviceUrl;

    @Override
    public ChatDto createChat(ChatDto chatDto) {
        return restTemplate.postForObject(crudMicroserviceUrl + "/api/v1/core/crud/chats", chatDto, ChatDto.class);
    }
}
