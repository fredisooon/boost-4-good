package fyodor.dev.chatmicroservice;

import fyodor.dev.chatmicroservice.ws.ChatWsController;
import fyodor.dev.chatmicroservice.ws.dto.ChatDto;
import fyodor.dev.chatmicroservice.ws.dto.MessageDto;
import fyodor.dev.chatmicroservice.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChatWsControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private ChatWsController chatWsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createChat_validSubscription_sendsChatDto() {
        UUID creatorId = UUID.randomUUID();
        UUID readerId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();

        ChatDto chatDto = ChatDto.builder()
                .id(chatId)
                .creatorId(creatorId)
                .readerId(readerId)
                .build();

        when(subscriptionService.hasValidSubscription(creatorId, readerId)).thenReturn(true);

        chatWsController.createChat(chatDto);

        ArgumentCaptor<ChatDto> captor = ArgumentCaptor.forClass(ChatDto.class);
        verify(messagingTemplate).convertAndSend(eq(ChatWsController.FETCH_MESSAGES
                .replace("{chatId}", chatDto.getId().toString())), captor.capture());
        ChatDto sentChatDto = captor.getValue();

        assertEquals(chatDto, sentChatDto);
    }

    @Test
    void createChat_invalidSubscription_throwsException() {
        UUID creatorId = UUID.randomUUID();
        UUID readerId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();

        ChatDto chatDto = ChatDto.builder()
                .id(chatId)
                .creatorId(creatorId)
                .readerId(readerId)
                .build();

        when(subscriptionService.hasValidSubscription(creatorId, readerId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> chatWsController.createChat(chatDto));
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(ChatDto.class));
    }

    @Test
    void sendMessage_sendsMessageDto() {
        UUID chatId = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();
        String content = "Top five numbers from 1 to 5";
        List<UUID> imageIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());

        MessageDto messageDto = MessageDto.builder()
                .senderId(senderId)
                .content(content)
                .imageIds(imageIds)
                .build();

        chatWsController.sendMessage(chatId, messageDto);

        ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
        verify(messagingTemplate).convertAndSend(eq(ChatWsController.FETCH_MESSAGES.replace("{chatId}",
                chatId.toString())), captor.capture());
        MessageDto sentMessageDto = captor.getValue();

        assertEquals(chatId, sentMessageDto.getChatId());
        assertEquals(senderId, sentMessageDto.getSenderId());
        assertEquals(content, sentMessageDto.getContent());
        assertEquals(imageIds, sentMessageDto.getImageIds());
        assertNotNull(sentMessageDto.getTimestamp());
    }
}
