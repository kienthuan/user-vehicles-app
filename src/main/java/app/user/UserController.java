package app.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.dao.User;
import app.security.config.CustomUserAuthentication;
import app.user.model.UserModel;
import app.user.model.AddVehicleList;
import app.user.model.ChangeVehicleOwnerModel;
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserModel registerUser(@Valid @RequestBody UserModel userModel) {
		logger.info("Register user...");
		return userService.registerUser(userModel);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public UserModel registerUser(@AuthenticationPrincipal CustomUserAuthentication userAuthentication) {
		logger.info("Get user...");
		User loginUser = (User)userAuthentication.getDetails();
		return userService.getUser(loginUser.getUserCode());
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(path = "/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<UserModel> registerVehicles(@RequestBody AddVehicleList vehiclesList) {
		logger.info("Register vehicle...");
		return userService.registerVehiclesWithUser(vehiclesList);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/vehicles/owner", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public UserModel changeVehiclesOwner(@RequestBody ChangeVehicleOwnerModel changeModel) {
		logger.info("Change vehicles owner...");
		return userService.changeOwnerShip(changeModel);
	}
}
