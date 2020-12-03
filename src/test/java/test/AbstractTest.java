package test;

import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.UserVehicleApplication;
import app.dao.User;
import app.dao.UserRoleEnum;
import app.repo.MaintenanceRepo;
import app.repo.UserRepo;
import app.repo.VehicleRepo;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = UserVehicleApplication.class)
@AutoConfigureMockMvc
public abstract class AbstractTest {
	
	protected static final String ADMIN_PASSWORD = "123";
	protected static final String ADMIN_EMAIL = "admin@gmail.com";

	@Autowired
	protected UserRepo userRepo;
	
	@Autowired
	protected VehicleRepo vehicleRepo;
	
	@Autowired
	protected MaintenanceRepo maintenanceRepo;
	
	@Autowired
	protected MockMvc mockMvc;

	@BeforeEach
	public final void initTest() {
		clearTestData();
		
		assertNotNull(mockMvc);
		assertNotNull(userRepo);
		assertNotNull(vehicleRepo);
		initAdminUser();
	}
	
	@AfterEach
	public final void clearTestData() {
		maintenanceRepo.deleteAll();
		vehicleRepo.deleteAll();
		userRepo.deleteAll();
	}
	
	private void initAdminUser() {
		User admin = new User();
		admin.setUserCode("82c10c2d-1b62-4b53-a32f-4d3d671ee514");
		admin.setFirstName("admin");
		admin.setLastName("admin");
		admin.setRole(UserRoleEnum.ROLE_ADMIN);
		admin.setEmail("admin@gmail.com");
		admin.setPassword("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
		userRepo.save(admin);
	}
	
	protected String objectToJson(Object object) {
		try {
			ObjectMapper jsonmapper = new ObjectMapper();
			jsonmapper.setSerializationInclusion(Include.NON_NULL);
			jsonmapper.setSerializationInclusion(Include.NON_EMPTY);
			return jsonmapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	protected <T> T extractResponseAsObject(final ResultActions resultActions, Class<T> clazz) {
		try {
			return jsonToObject(resultActions.andReturn().getResponse().getContentAsString(), clazz);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected <T> T jsonToObject(String json, Class<T> clazz) {
		try {
			ObjectMapper jsonmapper = new ObjectMapper();
			jsonmapper.setSerializationInclusion(Include.NON_NULL);
			jsonmapper.setSerializationInclusion(Include.NON_EMPTY);
			return jsonmapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected RequestPostProcessor givenAuthentication(String email, String password) {
		return SecurityMockMvcRequestPostProcessors.httpBasic(email, password);
	}
}
