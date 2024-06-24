package fyodor.dev.chatmicroservice.rest.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {

    UUID id;
    UUID chatId;
    UUID senderId;
    String content;
    LocalDateTime timestamp;
    List<UUID> imageIds;
}
