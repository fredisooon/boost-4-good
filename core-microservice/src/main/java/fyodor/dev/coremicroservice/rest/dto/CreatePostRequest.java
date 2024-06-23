package fyodor.dev.coremicroservice.rest.dto;

import fyodor.dev.coremicroservice.domain.image.Image;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreatePostRequest {

    private String title;
    private String content;
    private UUID subscriptionId;
    private List<UUID> imageIds;
}
