package app.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import app.dao.Maintenance;
import app.maintenance.model.MaintenanceModel;

@Mapper(componentModel = "spring",
	uses = MapperHelper.class,
	imports = UUID.class,
	unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaintenanceMapper {
	
	@Mapping(source = "id", target = "id", ignore = true)
	@Mapping(source = "maintenanceDate", target = "creationTimestamp", ignore = true)
	@Mapping(target = "maintenanceCode", expression = "java(UUID.randomUUID().toString())")
	public Maintenance toEntity(MaintenanceModel maintenanceModel);
	
	@Mapping(source = "creationTimestamp", target = "maintenanceDate", qualifiedByName = "mapTimestamp")
	@Mapping(source = "maintenanceCode", target = "id")
	@Mapping(source = "vehicle", target = "vehicleId", qualifiedByName = "mapVehicleMaintenance")
	public MaintenanceModel toModel(Maintenance maintenance);
}
