package kz.air_astana.flight_service.mapper;

import kz.air_astana.flight_service.model.dto.UserDto;
import kz.air_astana.flight_service.model.entitites.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User toEntity(UserDto userDto);

	UserDto toDto(User user);
}
