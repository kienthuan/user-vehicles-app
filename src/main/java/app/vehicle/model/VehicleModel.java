package app.vehicle.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.maintenance.model.MaintenanceModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleModel {

	private String id;
	
	@NotBlank
	private String name;
	
	private String owner;
	
	private List<MaintenanceModel> maintenanceHistory;
}
