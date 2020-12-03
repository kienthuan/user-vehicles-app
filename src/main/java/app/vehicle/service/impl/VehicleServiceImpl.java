package app.vehicle.service.impl;

import org.springframework.stereotype.Service;

import app.dao.Vehicle;
import app.exception.BusinessException;
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

	@Override
	public VehicleModel getVehicle(String vehicleCode) {
		Vehicle foundVehicle = vehicleRepo.findByVehicleCode(vehicleCode)
				.orElseThrow(() -> {
					String errorMsg = String.format("Cannot find vehicle with id %s", vehicleCode);
					return new BusinessException(errorMsg);
				});
		return vehicleMapper.toModel(foundVehicle);
	}
}
