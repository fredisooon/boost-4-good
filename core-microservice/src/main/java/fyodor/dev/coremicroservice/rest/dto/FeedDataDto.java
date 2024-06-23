package fyodor.dev.coremicroservice.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedDataDto {

    private List<PostDto> posts;
    private List<SubscriptionDto> blogs;
}
