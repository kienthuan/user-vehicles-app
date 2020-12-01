package app.mapper;

import java.util.Optional;
import java.util.UUID;

import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import app.dao.User;
import app.util.HashUtil;

@Service
public class MapperHelper {
	
	@Named("userMapperPasswordEncoder")
	public String encodePassword(String plainPassword) {
		return HashUtil.hash(plainPassword);
	}
	
	@Named("uuidGenerator")
	public String randomUUID() {
		return UUID.randomUUID().toString();
	}
	
	@Named("mapVehicleOwner")
	public String mapVehicleOwner(User owner) {
		return Optional.ofNullable(owner)
				.map(user -> user.getFirstName() + " " + user.getLastName())
				.orElse("");
	}
}
