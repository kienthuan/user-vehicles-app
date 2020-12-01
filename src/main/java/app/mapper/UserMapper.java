package app.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import app.dao.User;
import app.user.model.UserModel;

@Mapper(componentModel = "spring", uses = MapperHelper.class, imports = UUID.class)
public interface UserMapper {
	
	@Mapping(source = "password", target = "password", qualifiedByName = "userMapperPasswordEncoder")
	@Mapping(source = "id", target = "id", ignore = true)
	@Mapping(target = "userCode", expression = "java(UUID.randomUUID().toString())")
	public User toEntity(UserModel userModel);
	
	@Mapping(source = "password", target = "password", ignore = true)
	@Mapping(source = "userCode", target = "id")
	public UserModel toModel(User userEntity);
}
