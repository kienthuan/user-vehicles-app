package app.maintenance.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceModel {
	private String id;
	
	@NotNull
	@Min(value = 0, message = "Value must be positive")
	private BigDecimal cost;
	
	private String comment;
	
	private String maintenanceDate;
	
	private String vehicleId;
}
