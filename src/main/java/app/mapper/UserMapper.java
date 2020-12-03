package app.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import app.dao.User;
import app.dao.UserRoleEnum;
import app.user.model.UserModel;

@Mapper(componentModel = "spring", 
	uses = {MapperHelper.class, VehicleMapper.class},
	imports = {UUID.class, UserRoleEnum.class}, 
	unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	
	@Mapping(source = "password", target = "password", qualifiedByName = "userMapperPasswordEncoder")
	@Mapping(source = "id", target = "id", ignore = true)
	@Mapping(target = "userCode", expression = "java(UUID.randomUUID().toString())")
	@Mapping(target = "role", expression = "java(UserRoleEnum.ROLE_USER)")
	public User toEntity(UserModel userModel);
	
	@Mapping(source = "password", target = "password", ignore = true)
	@Mapping(source = "userCode", target = "id")
	@Mapping(source = "vehicles", target = "vehicleList")
	public UserModel toModel(User userEntity);
}
