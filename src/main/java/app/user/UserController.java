package app.user;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.user.model.UserModel;
import app.user.service.UserService;

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
		userModel.setPassword(null);
		return userModel;
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public UserModel get(Authentication authentiction) {
		logger.info("Get user...", authentiction);
		return userService.findByEmailAndPassword(String.valueOf(authentiction.getPrincipal()), String.valueOf(authentiction.getCredentials()));
	}
}
