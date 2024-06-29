package fyodor.dev.chatmicroservice.ws;

import fyodor.dev.chatmicroservice.config.RabbitMQConfig;
import fyodor.dev.chatmicroservice.service.ChatService;
import fyodor.dev.chatmicroservice.service.MessageService;
import fyodor.dev.chatmicroservice.ws.dto.ChatDto;
import fyodor.dev.chatmicroservice.ws.dto.MessageDto;
import fyodor.dev.chatmicroservice.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.UUID;

import static fyodor.dev.chatmicroservice.config.RabbitMQConfig.*;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Чат (веб-сокеты)", description = "Операции для работы с чатом по веб-сокет соединению")
public class ChatWsController {

    RabbitTemplate rabbitTemplate;
    SimpMessagingTemplate messagingTemplate;
    SubscriptionService subscriptionService;
    ChatService chatService;
    MessageService messageService;

    public static final String CREATE_CHAT = "/app/chats.create";
    public static final String SEND_MESSAGE = "/app/chats.{chatId}.messages.send";
    public static final String FETCH_MESSAGES = "/topic/chats.{chatId}.messages";

    @MessageMapping(CREATE_CHAT)
    @Operation(summary = "Создать новый чат", description = "Создание нового чата при наличии подписки нужного уровня")
    public void createChat(@Parameter(description = "Объект чата", required = true)
                               ChatDto chatDto) {
        boolean hasValidSubscription = subscriptionService
                .hasValidSubscription(chatDto.getCreatorId(), chatDto.getReaderId());
        if (!hasValidSubscription) {
            throw new RuntimeException("Invalid subscription level");
        }

        ChatDto createdChat = chatService.createChat(chatDto);

        messagingTemplate.convertAndSend(
                FETCH_MESSAGES.replace("{chatId}", chatDto.getId().toString()),
                chatDto
        );
        rabbitTemplate.convertAndSend(NEW_CHAT_TOPIC, NEW_CHAT_ROUTING_KEY, createdChat);
    }

    @MessageMapping(SEND_MESSAGE)
    @Operation(summary = "Отправка сообщения", description = "Отправка сообщений в чат")
    public void sendMessage(@Parameter(description = "Chat ID", required = true)
                                @DestinationVariable("chatId") UUID chatId,
                            @Parameter(description = "Message data transfer object", required = true)
                                MessageDto messageDto) {
        messageDto.setChatId(chatId);
        messageDto.setTimestamp(LocalDateTime.now());

        MessageDto sentMessage = messageService.sendMessage(messageDto);

        messagingTemplate.convertAndSend(
                FETCH_MESSAGES.replace("{chatId}", chatId.toString()),
                messageDto
        );
        rabbitTemplate.convertAndSend(UNREAD_MESSAGE_TOPIC, UNREAD_MESSAGE_ROUTING_KEY, sentMessage);
    }
}
