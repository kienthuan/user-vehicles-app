package app.user;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.dao.User;
import app.user.model.UserModel;
import app.user.model.UserVehicleList;
import app.user.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@NonNull
	private UserService userService;
	
	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserModel registerUser(@Valid @RequestBody UserModel userModel) {
		logger.info("Register user...");
		return userService.registerUser(userModel);
	}
	
	@PatchMapping(path = "/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public UserModel registerVehicles(@AuthenticationPrincipal User loginUser, @RequestBody UserVehicleList vehiclesList) {
		logger.info("Register vehicle...");
		return userService.registerVehiclesWithUser(loginUser, vehiclesList);
	}
}
