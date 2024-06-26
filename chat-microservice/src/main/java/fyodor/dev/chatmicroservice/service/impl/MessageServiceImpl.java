package fyodor.dev.chatmicroservice.service.impl;

import fyodor.dev.chatmicroservice.service.MessageService;
import fyodor.dev.chatmicroservice.ws.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final RestTemplate restTemplate;

    @Value("${crud.microservice.url}")
    private String crudMicroserviceUrl;

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        return restTemplate.postForObject(crudMicroserviceUrl + "/api/v1/core/crud/messages", messageDto, MessageDto.class);
    }
}
