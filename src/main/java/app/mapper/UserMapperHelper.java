package app.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import app.util.HashUtil;

@Service
public class UserMapperHelper {
	
	@Named("userMapperPasswordEncoder")
	public String encodePassword(String plainPassword) {
		return HashUtil.hash(plainPassword);
	}
}
