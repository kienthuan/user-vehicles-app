package test;

import app.user.model.UserModel;

public class TestDataFactory {

	public static UserModel buildUserModel() {
		UserModel userModel = new UserModel();
		userModel.setFirstName("test first name");
		userModel.setLastName("test last name");
		userModel.setEmail("test.user@gmail.com");
		userModel.setPassword("testpassword");
		
		return userModel;
	}
}
