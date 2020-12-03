package app.maintenance.service.inpl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import app.dao.Maintenance;
import app.dao.Vehicle;
import app.exception.BusinessException;
import app.maintenance.model.MaintenanceModel;
import app.maintenance.service.MaintenanceService;
import app.mapper.MaintenanceMapper;
import app.repo.MaintenanceRepo;
import app.repo.VehicleRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {
	
	private VehicleRepo vehicleRepo;
	
	private MaintenanceRepo maintenanceRepo;
	
	private MaintenanceMapper maintenanceMapper;

	@Override
	public MaintenanceModel addMaintenance(String vehicleId, MaintenanceModel maintenanceModel) {
		Vehicle foundVehicle = vehicleRepo.findByVehicleCode(vehicleId)
				.orElseThrow(() -> {
					String errorMsg = String.format("Cannot find vehicle with id %s", vehicleId);
					return new BusinessException(errorMsg);
				});
		
		Maintenance maintenance = maintenanceMapper.toEntity(maintenanceModel);
		maintenance.setVehicle(foundVehicle);		
		maintenanceRepo.save(maintenance);
		
		return maintenanceMapper.toModel(maintenance);
	}

	@Override
	public MaintenanceModel editMaintenance(String maintenanceId, MaintenanceModel model) {
		Maintenance foundMaintenance = findMaintenanceRecord(maintenanceId);
		foundMaintenance.setCost(model.getCost());
		
		String newComment = Optional.of(model.getComment())
				.filter(comment -> !StringUtils.isEmpty(model.getComment()))
				.map(StringUtils::trimWhitespace)
				.orElse(foundMaintenance.getComment());
		foundMaintenance.setComment(newComment);
		
		maintenanceRepo.save(foundMaintenance);
		return maintenanceMapper.toModel(foundMaintenance);
	}

	private Maintenance findMaintenanceRecord(String maintenanceId) {
		Maintenance foundMaintenance = maintenanceRepo.findByMaintenanceCode(maintenanceId)
				.orElseThrow(() -> {
					String errorMsg = String.format("Cannot find maintenance record with id %s", maintenanceId);
					return new BusinessException(errorMsg);
				});
		return foundMaintenance;
	}

	@Override
	public String deleteMaintenance(String maintenanceId) {
		Maintenance foundMaintenance = findMaintenanceRecord(maintenanceId);
		maintenanceRepo.delete(foundMaintenance);
		return maintenanceId;
	}

	@Override
	public MaintenanceModel getMaintenance(String maintenanceId) {
		Maintenance foundMaintenance = findMaintenanceRecord(maintenanceId);
		return maintenanceMapper.toModel(foundMaintenance);
	}
}
