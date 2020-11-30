package thuan.ngo.user;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import thuan.ngo.user.model.UserModel;
import thuan.ngo.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private UserService userService;
	
	public UserController(UserService userService) {
		logger.info("INIT USER CONTROLLER...");
		this.userService = userService;
	}
	
	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserModel register(@Valid @RequestBody UserModel userModel) {
		logger.info("Post user...");
		userService.registerUser(userModel);
		return userService.findByEmailAndPassword(userModel.getEmail(), userModel.getPassword());
	}
}
