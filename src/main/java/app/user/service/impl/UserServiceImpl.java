package app.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.UserVehicleApplication;
import app.dao.User;
import app.dao.Vehicle;
import app.exception.BusinessException;
import app.mapper.UserMapper;
import app.repo.UserRepo;
import app.repo.VehicleRepo;
import app.user.model.UserModel;
import app.user.model.AddVehicleList;
import app.user.model.ChangeVehicleOwnerModel;
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
	public List<UserModel> registerVehiclesWithUser(AddVehicleList addVehiclesList) {
		if(addVehiclesList.isEmpty()) {
			throw new BusinessException("Empty list...");
		}
		
		List<User> foundUsers = this.findUsersFromList(addVehiclesList.keySet());
		List<UserModel> resultList = new ArrayList<>();
		for(User foundUser : foundUsers) {
			this.addVehicleForUser(foundUser, addVehiclesList);
			resultList.add(userMapper.toModel(foundUser));
		}
		
		return resultList;
	}

	private List<User> findUsersFromList(Set<String> userCodesList) {
		List<User> foundUsersList = userRepo.findByUserCodeIn(userCodesList);
		List<String> foundUserCodesList = foundUsersList.stream()
				.map(User::getUserCode).collect(Collectors.toList());
		if(foundUserCodesList.size() != userCodesList.stream().distinct().count()) {
			List<String> notFoundUserCodes = userCodesList.stream()
					.filter(userCode -> !foundUserCodesList.contains(userCode))
					.collect(Collectors.toList());
			String errorMessage = String.format("Not found user(s) with id : %s", notFoundUserCodes);
			throw new BusinessException(errorMessage);
		}
		return foundUsersList;
	}
	
	private void addVehicleForUser(User mayBeOwner, AddVehicleList addVehiclesList) {
		List<String> addVehicleCodesList = addVehiclesList.get(mayBeOwner.getUserCode());
		List<Vehicle> foundVehiclesList = vehicleRepo.findByVehicleCodeIn(addVehicleCodesList);
		List<String> foundVehicleCodesList = foundVehiclesList.stream()
				.map(Vehicle::getVehicleCode).collect(Collectors.toList());
		
		this.validateNotFoundVehicles(foundVehicleCodesList, addVehicleCodesList);
		this.validateAlreadyAssignVehicles(foundVehiclesList);
		
		foundVehiclesList.forEach(vehicle -> vehicle.setOwner(mayBeOwner));
		mayBeOwner.setVehicles(foundVehiclesList);
		
		userRepo.save(mayBeOwner);
		vehicleRepo.saveAll(foundVehiclesList);
	}
	
	private void validateNotFoundVehicles(List<String> foundVehicleCodesList, List<String> addVehicleCodesList) {
		List<String> notFoundUserCodes = addVehicleCodesList.stream()
				.filter(userCode -> !foundVehicleCodesList.contains(userCode))
				.collect(Collectors.toList());
		if(!notFoundUserCodes.isEmpty()) {
			String errorMessage = String.format("Not found vehicle(s) with id : %s", notFoundUserCodes);
			throw new BusinessException(errorMessage);
		}
	}
	
	private void validateAlreadyAssignVehicles(List<Vehicle> foundVehiclesList) {
		List<String> alreadyAssignVehicles = foundVehiclesList.stream()
				.filter(vehicle -> vehicle.getOwner() != null)
				.map(Vehicle::getVehicleCode)
				.collect(Collectors.toList());
		if(!alreadyAssignVehicles.isEmpty()) {
			String errorMessage = String.format("Already assigned owner vehicle(s) with id : %s", alreadyAssignVehicles);
			throw new BusinessException(errorMessage);
		}
	}

	@Override
	public UserModel getUser(String userCode) {
		User foundUser = userRepo.findByUserCode(userCode)
				.orElseThrow(() -> new BusinessException("User not found..."));
		return userMapper.toModel(foundUser);
	}

	@Override
	public UserModel changeOwnerShip(ChangeVehicleOwnerModel changeModel) {
		Set<String> foundUsersSet = Stream.of(changeModel.getFromOwnerId(), changeModel.getToOwnerId())
				.collect(Collectors.toSet());
		List<User> foundUsersList = this.findUsersFromList(foundUsersSet);

		User fromUser = foundUsersList.stream()
				.filter(user -> changeModel.getFromOwnerId().equals(user.getUserCode()))
				.findFirst().orElse(null);
		User toUser = foundUsersList.stream()
				.filter(user -> changeModel.getToOwnerId().equals(user.getUserCode()))
				.findFirst().orElse(null);
		this.changeOwnership(fromUser, toUser, changeModel.getVehiclesList());
		
		return userMapper.toModel(toUser);
	}
	
	private void changeOwnership(User fromUser, User toUser, List<String> changeVehiclesList) {
		if(fromUser == null || toUser == null || changeVehiclesList == null || changeVehiclesList.isEmpty()) {
			return;
		}
		
		List<Vehicle> foundVehiclesList = vehicleRepo.findByVehicleCodeIn(changeVehiclesList);
		List<String> foundVehicleCodesList = foundVehiclesList.stream()
				.map(Vehicle::getVehicleCode).collect(Collectors.toList());
		this.validateNotFoundVehicles(foundVehicleCodesList, changeVehiclesList);
		
		for(Vehicle foundVehicle : foundVehiclesList) {
			this.validateOwnership(fromUser, foundVehicle);
			foundVehicle.setOwner(toUser);
		}
		
		vehicleRepo.saveAll(foundVehiclesList);
	}
	
	private void validateOwnership(User fromUser, Vehicle vehicle) {
		if(vehicle.getOwner() != null && fromUser.getId() != vehicle.getOwner().getId()) {
			String errorMessage = String.format("Vehicle %s does not belong to user %s", vehicle.getVehicleCode(), fromUser.getUserCode());
			throw new BusinessException(errorMessage);
		}
	}
}
