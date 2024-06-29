package fyodor.dev.chatmicroservice.listener;

import fyodor.dev.chatmicroservice.ws.dto.ChatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static fyodor.dev.chatmicroservice.config.RabbitMQConfig.NEW_CHAT_QUEUE;

@Slf4j
@Component
public class ChatNotificationListener {

    @RabbitListener(queues = NEW_CHAT_QUEUE)
    public void handleNewChatNotification(ChatDto chatDto) {
        // TODO [27.06.2024]: implement notification logic
        log.info("New chat created: {}", chatDto);
    }
}
