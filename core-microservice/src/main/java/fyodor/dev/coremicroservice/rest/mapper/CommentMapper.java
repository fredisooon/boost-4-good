package fyodor.dev.coremicroservice.rest.mapper;

import fyodor.dev.coremicroservice.domain.feed.Comment;
import fyodor.dev.coremicroservice.rest.dto.CommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);
}
