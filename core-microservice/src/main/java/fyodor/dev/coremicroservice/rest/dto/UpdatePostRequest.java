package fyodor.dev.coremicroservice.rest.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdatePostRequest {

    private String title;
    private String content;
    private List<UUID> imageIds;
}
