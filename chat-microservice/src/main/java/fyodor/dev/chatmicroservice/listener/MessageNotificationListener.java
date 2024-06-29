package fyodor.dev.chatmicroservice.listener;

import fyodor.dev.chatmicroservice.ws.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static fyodor.dev.chatmicroservice.config.RabbitMQConfig.UNREAD_MESSAGE_QUEUE;

@Slf4j
@Component
public class MessageNotificationListener {

    @RabbitListener(queues = UNREAD_MESSAGE_QUEUE)
    public void handleUnreadMessageNotification(MessageDto messageDto) {
        // TODO [27.06.2024]: implement validate message status (send, delivered, read)
        log.info("Unread message: {}", messageDto);
    }
}
