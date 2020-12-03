package app.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import app.dao.Vehicle;
import app.vehicle.model.VehicleModel;

@Mapper(componentModel = "spring",
	uses = {MapperHelper.class, MaintenanceMapper.class},
	imports = UUID.class)
public interface VehicleMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "owner", ignore = true)
	@Mapping(target = "vehicleCode", expression = "java(UUID.randomUUID().toString())")
	public Vehicle toEntity(VehicleModel vehicleModel);
	
	@Mapping(source = "vehicleCode", target = "id")
	@Mapping(source = "owner", target = "owner", qualifiedByName = "mapVehicleOwner")
	@Mapping(source = "maintenances", target = "maintenanceHistory")
	public VehicleModel toModel(Vehicle vehicleEntity);
}
