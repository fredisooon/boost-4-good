package fyodor.dev.coremicroservice.rest.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCommentRequest {

    private UUID postId;
    private String content;
}
