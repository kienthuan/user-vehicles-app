package app.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.UserVehicleApplication;
import app.dao.User;
import app.exception.BusinessException;
import app.mapper.UserMapper;
import app.repo.UserRepo;
import app.user.model.UserModel;
import app.user.service.UserService;
import app.user.validator.UserValidator;
import app.util.HashUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	
	private UserRepo userRepo;
	
	private UserValidator userValidator;
	
	private final Logger logger = LoggerFactory.getLogger(UserVehicleApplication.class);
	
	@Override
	public void registerUser(UserModel userModel) {
		logger.info("register user...");
		userValidator.withRepo(userRepo).emailExisted(userModel.getEmail());
		User userEntity = userMapper.toEntity(userModel);
		userRepo.save(userEntity);
	}

	@Override
	public UserModel findByEmailAndPassword(String email, String password) {
		logger.info("finding user...");		
		String passwordHash = HashUtil.hash(password);
		User foundUser = userRepo.findByEmailAndPassword(email, passwordHash)
				.orElseThrow(() -> new BusinessException("User not found"));
		return userMapper.toModel(foundUser);
	}
}
