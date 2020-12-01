package app.vehicle;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import app.vehicle.model.VehicleModel;
import app.vehicle.service.VehicleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@NonNull
	private VehicleService vehicleService;
	
	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public VehicleModel register(@Valid @RequestBody VehicleModel vehicleModel) {
		logger.info("Register vehicle...");
		return vehicleService.registerVehicle(vehicleModel);
	}
}
