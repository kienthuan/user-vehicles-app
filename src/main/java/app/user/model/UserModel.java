package app.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Email
	private String email;
}
