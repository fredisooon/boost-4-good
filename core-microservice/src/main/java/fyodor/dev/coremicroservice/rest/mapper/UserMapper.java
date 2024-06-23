package fyodor.dev.coremicroservice.rest.mapper;

import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.rest.dto.CreateUserRequest;
import fyodor.dev.coremicroservice.rest.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto userDto);

//    @Mappings({@Mapping(target = "image", source = "image")})
//            @Mapping(target = "imageId", source = "image")})
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true) // Не маппим id, так как оно генерируется
    User toEntity(CreateUserRequest request);
}
