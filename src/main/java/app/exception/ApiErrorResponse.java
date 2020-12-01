package app.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class ApiErrorResponse {

	private String errorId = "error-".concat(UUID.randomUUID().toString());
	
	private List<String> errorMessages = new ArrayList<>();
}
