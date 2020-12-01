package app.user.validator;

import org.springframework.stereotype.Service;

import app.exception.BusinessException;
import app.repo.UserRepo;

@Service
public class UserValidator {

	private UserRepo userRepo;
	
	public void emailExisted(String email) {
		if(userRepo.findByEmail(email).isPresent()) {
			throw new BusinessException("Email is used...");
		}
	}
	
	public UserValidator withRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
		return this;
	}
}
