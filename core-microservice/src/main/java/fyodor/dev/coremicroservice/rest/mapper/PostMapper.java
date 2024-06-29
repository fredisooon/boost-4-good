package fyodor.dev.coremicroservice.rest.mapper;

import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.rest.dto.CreatePostRequest;
import fyodor.dev.coremicroservice.rest.dto.PostDto;
import fyodor.dev.coremicroservice.rest.dto.UpdatePostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subscription.id", source = "subscriptionId")
    @Mapping(target = "user.id", source = "creatorId")
    Post toEntity(CreatePostRequest request);

    @Mapping(target = "subscriptionId", source = "subscription.id")
    @Mapping(target = "creatorId", source = "user.id")
    PostDto toDto(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updatePostFromDto(UpdatePostRequest request, @MappingTarget Post post);
}