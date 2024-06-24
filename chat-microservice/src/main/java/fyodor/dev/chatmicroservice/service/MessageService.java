package fyodor.dev.chatmicroservice.service;

import fyodor.dev.chatmicroservice.rest.dto.MessageDto;

public interface MessageService {

    void sendMessage(MessageDto messageDto);
}
