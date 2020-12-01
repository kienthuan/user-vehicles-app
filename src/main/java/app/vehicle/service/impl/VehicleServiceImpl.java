package app.vehicle.service.impl;

import org.springframework.stereotype.Service;

import app.dao.Vehicle;
import app.mapper.VehicleMapper;
import app.repo.VehicleRepo;
import app.vehicle.model.VehicleModel;
import app.vehicle.service.VehicleService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {
	
	private VehicleRepo vehicleRepo;
	
	private VehicleMapper vehicleMapper;
	
	@Override
	public VehicleModel registerVehicle(VehicleModel vehicleModel) {
		Vehicle vehicleEntity = vehicleMapper.toEntity(vehicleModel);
		vehicleRepo.save(vehicleEntity);
		return vehicleMapper.toModel(vehicleEntity);
	}
}
