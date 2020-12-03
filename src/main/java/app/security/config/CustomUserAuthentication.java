package app.security.config;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import app.dao.User;
import app.dao.UserRoleEnum;
import lombok.NonNull;

public class CustomUserAuthentication implements Authentication {

	private static final long serialVersionUID = 1330592970271045790L;
	
	@NonNull
	private transient User user;
	private boolean authenticated = false;
	private Collection<GrantedAuthority> authorities;
	
	public CustomUserAuthentication(User user) {
		this.user = user;
		if(this.user != null) {
			this.authenticated = true;
			setupAuthorities();
		}
	}
	
	private void setupAuthorities() {
		this.authorities = Stream.of(new SimpleGrantedAuthority(user.getRole().name())).collect(Collectors.toList());
		if(UserRoleEnum.ROLE_ADMIN.equals(user.getRole())) {
			this.authorities.add(new SimpleGrantedAuthority(UserRoleEnum.ROLE_USER.name()));
		}
	}

	@Override
	public String getName() {
		return user.getFirstName().concat(" ").concat(user.getLastName());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return user.getEmail();
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user.getEmail();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;		
	}

}
