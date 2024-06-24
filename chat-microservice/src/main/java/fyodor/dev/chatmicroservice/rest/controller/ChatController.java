package fyodor.dev.chatmicroservice.rest.controller;

import fyodor.dev.chatmicroservice.rest.dto.ChatDto;
import fyodor.dev.chatmicroservice.rest.dto.MessageDto;
import fyodor.dev.chatmicroservice.service.ChatFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatFacadeService chatFacadeService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageDto sendMessage(MessageDto messageDto) {
        return chatFacadeService.sendMessage(messageDto);
    }

    @MessageMapping("/startChat")
    @SendTo("/topic/chat")
    public ChatDto startChat(UUID creatorId, UUID readerId) {
        return chatFacadeService.startChat(creatorId, readerId);
    }
}
