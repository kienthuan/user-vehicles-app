package app.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import app.dao.User;
import app.dao.Vehicle;
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
	
	@Named("mapTimestamp")
	public String mapTimestamp(LocalDateTime localDateTime) {
		final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:MM:ss");
		return Optional.ofNullable(localDateTime)
				.map(timestamp -> timestamp.format(dateTimeFormat))
				.orElse("Fail to format timestamp");
	}
	
	@Named("mapVehicleMaintenance")
	public String mapVehicleMaintenance(Vehicle vehicle) {
		return Optional.ofNullable(vehicle)
				.map(Vehicle::getVehicleCode)
				.orElse("Fail to map Vehicle");
	}
}
