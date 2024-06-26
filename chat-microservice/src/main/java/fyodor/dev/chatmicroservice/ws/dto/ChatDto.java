package fyodor.dev.chatmicroservice.ws.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    UUID id;
    UUID creatorId;
    UUID readerId;
}
