package test.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import app.dao.User;
import app.mapper.UserMapper;
import app.user.model.UserModel;
import test.AbstractTest;

public class TestUserMapper extends AbstractTest {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testMap_Model_To_Entity() {
		UserModel userModel = new UserModel();
		userModel.setFirstName("test first name");
		userModel.setLastName("test last name");
		userModel.setEmail("test.user@gmail.com");
		userModel.setPassword("testpassword");
		
		String passwordHash = Base64.getEncoder().encodeToString(userModel.getPassword().getBytes());
		User userEntity = userMapper.toEntity(userModel);
		assertNotNull(userEntity);
		assertEquals(userModel.getFirstName(), userEntity.getFirstName());
		assertEquals(userModel.getLastName(), userEntity.getLastName());
		assertEquals(passwordHash, userEntity.getPassword());
	}
}

