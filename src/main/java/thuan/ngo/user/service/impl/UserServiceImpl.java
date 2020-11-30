package thuan.ngo.user.service.impl;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import thuan.ngo.UserVehicleApplication;
import thuan.ngo.dao.User;
import thuan.ngo.exception.BusinessException;
import thuan.ngo.mapper.UserMapper;
import thuan.ngo.repo.UserRepo;
import thuan.ngo.user.model.UserModel;
import thuan.ngo.user.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	
	private UserRepo userRepo;
	
	private final Logger logger = LoggerFactory.getLogger(UserVehicleApplication.class);
	
	@Override
	public void registerUser(UserModel userModel) {
		logger.info("register user...");
		User userEntity = userMapper.toEntity(userModel);
		userRepo.save(userEntity);
	}

	@Override
	public UserModel findByEmailAndPassword(String email, String password) {
		logger.info("finding user...");
		String passwordHash = Base64.getEncoder().encodeToString(password.getBytes());
		User foundUser = userRepo.findByEmailAndPassword(email, passwordHash)
				.orElseThrow(() -> new BusinessException("User not found"));
		return userMapper.toModel(foundUser);
	}
}
