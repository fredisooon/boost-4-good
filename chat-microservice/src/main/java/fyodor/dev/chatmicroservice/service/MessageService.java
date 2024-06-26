package fyodor.dev.chatmicroservice.service;

import fyodor.dev.chatmicroservice.ws.dto.MessageDto;

public interface MessageService {
    
    MessageDto sendMessage(MessageDto messageDto);
}
