package app.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import app.exception.ApiErrorResponse;
import app.exception.BusinessException;

@Mapper(componentModel = "spring")
public interface ApiErrorResponseMapper {
	
	@Named("mapSingleErrorMessage")
	public static List<String> mapSingleErrorMessage(String errorMessage) {
		return Stream.of(errorMessage).collect(Collectors.toList());
	}
	
	@Named("mapMethodArgumentNotValidException")
	public static List<String> mapSingleErrorMessage(BindingResult bindingResult) {
		return bindingResult.getFieldErrors().stream()
				.map(error -> error.getField() + ":" + error.getDefaultMessage())
				.collect(Collectors.toList());
	}
	
	@Mapping(source = "errorMessage", target = "errorMessages", qualifiedByName = "mapSingleErrorMessage")
	ApiErrorResponse toApiErrorResponse(BusinessException businessException);
	
	@Mapping(source = "bindingResult", target = "errorMessages", qualifiedByName = "mapMethodArgumentNotValidException")
	ApiErrorResponse toApiErrorResponse(MethodArgumentNotValidException ex);
}
