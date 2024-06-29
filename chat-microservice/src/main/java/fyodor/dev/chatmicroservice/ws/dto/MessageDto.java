package fyodor.dev.chatmicroservice.ws.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {

    UUID id;
    UUID chatId;
    UUID senderId;
    String content;
    LocalDateTime timestamp;
    List<UUID> imageIds;
}
