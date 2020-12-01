package app.user.validator;

import app.exception.BusinessException;
import app.repo.UserRepo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserValidator {

	private UserRepo userRepo;
	
	public void validateEmail(String email) {
		if(userRepo.findByEmail(email).isPresent()) {
			throw new BusinessException("Email is used...");
		}
	}
}
