package thuan.ngo.mapper;

import org.mapstruct.Mapper;

import thuan.ngo.dao.User;
import thuan.ngo.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
	public User toEntity(UserModel userModel);
}
