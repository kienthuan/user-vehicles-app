package thuan.ngo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 8256274758461716074L;

	private String errorId;
	
	private String errorMessage;
	
	public BusinessException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
