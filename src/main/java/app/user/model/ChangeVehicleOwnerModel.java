package app.user.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeVehicleOwnerModel {

	@NotBlank
	private String fromOwnerId;
	
	@NotBlank
	private String toOwnerId;
	
	@NotEmpty
	private List<String> vehiclesList;
}
