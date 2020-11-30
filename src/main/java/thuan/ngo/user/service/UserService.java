package thuan.ngo.user.service;

import thuan.ngo.user.model.UserModel;

public interface UserService {

	public void registerUser(UserModel userModel);
	
	public UserModel findByEmailAndPassword(String email, String password);
}
