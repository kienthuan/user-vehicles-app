package thuan.ngo.service;

import java.util.Optional;

import thuan.ngo.dao.User;

public interface UserService {

	//public void registerUser()
	public Optional<User> findByFullName(String firstName, String lastName);
}
