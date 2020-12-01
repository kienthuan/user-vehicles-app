package test;

import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.UserVehicleApplication;
import app.repo.UserRepo;
import app.repo.VehicleRepo;
import test.config.TestJpaDbConfig;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = {UserVehicleApplication.class, TestJpaDbConfig.class})
@AutoConfigureMockMvc
public abstract class AbstractTest {

	@Autowired
	protected UserRepo userRepo;
	
	@Autowired
	protected VehicleRepo vehicleRepo;
	
	@Autowired
	protected MockMvc mockMvc;

	@BeforeEach
	public final void initTest() {
		assertNotNull(mockMvc);
		assertNotNull(userRepo);
		assertNotNull(vehicleRepo);
		
		vehicleRepo.deleteAll();
		userRepo.deleteAll();
	}
	
	protected String objectToJson(Object object) {
		try {
			ObjectMapper jsonmapper = new ObjectMapper();
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
