package app.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import app.dao.User;
import app.exception.BusinessException;
import app.repo.UserRepo;
import app.util.HashUtil;

public class CustomUserAuthenticationProvider implements AuthenticationProvider {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private UserRepo userRepo;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (invalidUserName(authentication) || invalidPassword(authentication)) {
			logger.error("Invalid authentication...");
			throw new BusinessException("Invalid authentication...");
		}
		String email = String.valueOf(authentication.getPrincipal());
		String password = String.valueOf(authentication.getCredentials());
		
		User user = userRepo.findByEmailAndPassword(email, HashUtil.hash(password)).orElseThrow(() -> new BusinessException("Login fail..."));
		return new CustomUserAuthentication(user);
	}

	private boolean invalidUserName(Authentication auth) {
		return auth.getPrincipal() == null
				|| !(auth.getPrincipal() instanceof String || "".equals(auth.getPrincipal()));
	}
	
	private boolean invalidPassword(Authentication auth) {
		return auth.getCredentials() == null
				|| !(auth.getCredentials() instanceof String || "".equals(auth.getCredentials()));
	}
	
	@Autowired
	public void setUserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UsernamePasswordAuthenticationToken.class) ||
				clazz.equals(CustomUserAuthentication.class);
	}
}
