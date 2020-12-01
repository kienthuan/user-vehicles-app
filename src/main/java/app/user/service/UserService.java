package app.user.service;

import app.user.model.UserModel;

public interface UserService {

	public void registerUser(UserModel userModel);
	
	public UserModel findByEmailAndPassword(String email, String password);
}
