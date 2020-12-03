package test;

import app.user.model.UserModel;
import app.vehicle.model.VehicleModel;

public class TestDataFactory {

	public static UserModel buildUserModel() {	
		return buildUserModel("test first name", "test last name", "test.user@gmail.com", "testpassword");
	}
	
	public static UserModel buildUserModel(String firstName, String lastName, String email, String password) {
		UserModel userModel = new UserModel();
		userModel.setFirstName(firstName);
		userModel.setLastName(lastName);
		userModel.setEmail(email);
		userModel.setPassword(password);
		
		return userModel;
	}
	
	public static VehicleModel buildVehicleModel() {
		VehicleModel userModel = new VehicleModel();
		userModel.setName("Mazda 2003");
		
		return userModel;
	}
}
