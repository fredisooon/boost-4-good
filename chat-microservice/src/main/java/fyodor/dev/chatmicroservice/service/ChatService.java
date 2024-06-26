package fyodor.dev.chatmicroservice.service;

import fyodor.dev.chatmicroservice.ws.dto.ChatDto;

public interface ChatService {

    ChatDto createChat(ChatDto chatDto);
}
