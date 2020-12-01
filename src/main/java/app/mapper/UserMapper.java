package app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import app.dao.User;
import app.user.model.UserModel;

@Mapper(componentModel = "spring", uses = UserMapperHelper.class)
public interface UserMapper {
	
	@Mapping(source = "password", target = "password", qualifiedByName = "userMapperPasswordEncoder")
	public User toEntity(UserModel userModel);
	
	@Mapping(source = "password", target = "password", ignore = true)
	public UserModel toModel(User userEntity);
}
