package app.maintenance;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.maintenance.model.MaintenanceModel;
import app.maintenance.service.MaintenanceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@NonNull
	private MaintenanceService maintenanceService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path = "/{vehicleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public MaintenanceModel addMaintenance(@PathVariable(name = "vehicleId") String vehicleId,
			@Valid @RequestBody MaintenanceModel maintenanceModel) {
		logger.info("Register vehicle's maintenance...");
		return maintenanceService.addMaintenance(vehicleId, maintenanceModel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/{maintenanceId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public MaintenanceModel editMaintenance(@PathVariable(name = "maintenanceId") String maintenanceId,
			@Valid @RequestBody MaintenanceModel maintenanceModel) {
		logger.info("Edit vehicle's maintenance...");
		return maintenanceService.editMaintenance(maintenanceId, maintenanceModel);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/{maintenanceId}")
	@ResponseStatus(code = HttpStatus.OK)
	public String deleteMaintenance(@PathVariable(name = "maintenanceId") String maintenanceId) {
		logger.info("Delete vehicle's maintenance...");
		return maintenanceService.deleteMaintenance(maintenanceId);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path = "/{maintenanceId}")
	@ResponseStatus(code = HttpStatus.OK)
	public MaintenanceModel getMaintenance(@PathVariable(name = "maintenanceId") String maintenanceId) {
		logger.info("Get vehicle's maintenance...");
		return maintenanceService.getMaintenance(maintenanceId);
	}
}
