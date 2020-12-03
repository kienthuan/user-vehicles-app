package app.user.service;

import java.util.List;

import app.user.model.UserModel;
import app.user.model.AddVehicleList;
import app.user.model.ChangeVehicleOwnerModel;

public interface UserService {

	public UserModel getUser(String userCode);
	
	public UserModel registerUser(UserModel userModel);
	
	public UserModel changeOwnerShip(ChangeVehicleOwnerModel changeModel);
	
	public List<UserModel> registerVehiclesWithUser(AddVehicleList vehiclesList);
}
