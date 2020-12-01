package app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import app.mapper.ApiErrorResponseMapper;
import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	private ApiErrorResponseMapper apiErrorResponseMapper;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(apiErrorResponseMapper.toApiErrorResponse(ex), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorResponse> businessExceptionHandler(BusinessException exception,
			WebRequest request) {
		return new ResponseEntity<>(apiErrorResponseMapper.toApiErrorResponse(exception), HttpStatus.BAD_REQUEST);
	}
}
