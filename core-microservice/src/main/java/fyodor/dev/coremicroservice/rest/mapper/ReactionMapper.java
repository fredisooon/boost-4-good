package fyodor.dev.coremicroservice.rest.mapper;

import fyodor.dev.coremicroservice.domain.feed.Reaction;
import fyodor.dev.coremicroservice.rest.dto.ReactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReactionMapper {
    
    ReactionDto toDto(Reaction reaction);
}
