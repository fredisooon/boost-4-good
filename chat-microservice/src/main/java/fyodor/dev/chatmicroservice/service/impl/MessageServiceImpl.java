package fyodor.dev.chatmicroservice.service.impl;

import fyodor.dev.chatmicroservice.rest.dto.MessageDto;
import fyodor.dev.chatmicroservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final RabbitTemplate rabbitTemplate;

    private final String queueName = "chat-queue";

    @Override
    public void sendMessage(MessageDto messageDto) {
        rabbitTemplate.convertAndSend(queueName, messageDto);
    }
}
