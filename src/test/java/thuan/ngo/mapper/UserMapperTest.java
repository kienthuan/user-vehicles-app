package thuan.ngo.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.config.AbstractTest;
import thuan.ngo.dao.User;
import thuan.ngo.model.UserModel;

public class UserMapperTest extends AbstractTest {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testMap_Model_To_Entity() {
		UserModel userModel = new UserModel();
		userModel.setFirstName("test first name");
		userModel.setLastName("test last name");
		userModel.setPassword("testpassword");
		
		User userEntity = userMapper.toEntity(userModel);
		assertNotNull(userEntity);
		assertEquals(userModel.getFirstName(), userEntity.getFirstName());
		assertEquals(userModel.getLastName(), userEntity.getLastName());
		assertEquals(userModel.getPassword(), userEntity.getPassword());
	}
}

