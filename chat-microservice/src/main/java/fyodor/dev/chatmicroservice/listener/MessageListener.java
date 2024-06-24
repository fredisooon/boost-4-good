package fyodor.dev.chatmicroservice.listener;

import fyodor.dev.chatmicroservice.rest.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageListener {

    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "chat-queue")
    public void receiveMessage(MessageDto messageDto) {
        messagingTemplate.convertAndSend("/topic/messages", messageDto);
    }
}
