package fyodor.dev.chatmicroservice.service;

import fyodor.dev.chatmicroservice.rest.dto.ChatDto;
import fyodor.dev.chatmicroservice.rest.dto.MessageDto;

import java.util.UUID;

public interface ChatFacadeService {

    ChatDto startChat(UUID creatorId, UUID readerId);
    MessageDto sendMessage(MessageDto messageDto);
}
