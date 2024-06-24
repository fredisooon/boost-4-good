package fyodor.dev.chatmicroservice.rest.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatDto {

    UUID id;
    UUID creatorId;
    UUID readerId;
}
