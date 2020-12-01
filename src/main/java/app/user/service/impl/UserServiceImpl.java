package app.user.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.UserVehicleApplication;
import app.dao.User;
import app.dao.Vehicle;
import app.mapper.UserMapper;
import app.repo.UserRepo;
import app.repo.VehicleRepo;
import app.user.model.UserModel;
import app.user.model.UserVehicleList;
import app.user.service.UserService;
import app.user.validator.UserValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@NonNull
	private UserMapper userMapper;

	@NonNull
	private UserRepo userRepo;

	@NonNull
	private VehicleRepo vehicleRepo;

	private UserValidator userValidator;

	@PostConstruct
	public void initDependencies() {
		userValidator = new UserValidator(userRepo);
	}

	private final Logger logger = LoggerFactory.getLogger(UserVehicleApplication.class);

	@Override
	public UserModel registerUser(UserModel userModel) {
		logger.info("register user...");
		userValidator.validateEmail(userModel.getEmail());

		User userEntity = userMapper.toEntity(userModel);
		userRepo.save(userEntity);
		return userMapper.toModel(userEntity);
	}

	@Override
	public UserModel registerVehiclesWithUser(User userDao, UserVehicleList vehiclesList) {
		List<Vehicle> foundVehicleList = vehicleRepo.findByVehicleCodeIn(vehiclesList);
		userDao.setVehicles(foundVehicleList);
		return userMapper.toModel(userDao);
	}
}
