package fyodor.dev.chatmicroservice.ws;

import fyodor.dev.chatmicroservice.ws.dto.ChatDto;
import fyodor.dev.chatmicroservice.ws.dto.MessageDto;
import fyodor.dev.chatmicroservice.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Чат (веб-сокеты)", description = "Операции для работы с чатом по веб-сокет соединению")
public class ChatWsController {

    SimpMessagingTemplate messagingTemplate;
    SubscriptionService subscriptionService;

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

        messagingTemplate.convertAndSend(
                FETCH_MESSAGES.replace("{chatId}", chatDto.getId().toString()),
                chatDto
        );
    }

    @MessageMapping(SEND_MESSAGE)
    @Operation(summary = "Отправка сообщения", description = "Отправка сообщений в чат")
    public void sendMessage(@Parameter(description = "Chat ID", required = true)
                                @DestinationVariable("chatId") UUID chatId,
                            @Parameter(description = "Message data transfer object", required = true)
                                MessageDto messageDto) {
        messageDto.setChatId(chatId);
        messageDto.setTimestamp(LocalDateTime.now());

        messagingTemplate.convertAndSend(
                FETCH_MESSAGES.replace("{chatId}", chatId.toString()),
                messageDto
        );
    }
}
