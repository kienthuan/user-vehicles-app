package app.vehicle.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
}
