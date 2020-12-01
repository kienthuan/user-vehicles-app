package app.user.service;

import app.dao.User;
import app.user.model.UserModel;
import app.user.model.UserVehicleList;

public interface UserService {

	public UserModel registerUser(UserModel userModel);
	
	public UserModel registerVehiclesWithUser(User userDao, UserVehicleList vehiclesList);
}
