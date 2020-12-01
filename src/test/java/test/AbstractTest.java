package test;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.UserVehicleApplication;
import app.repo.UserRepo;
import test.config.TestJpaDbConfig;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = {UserVehicleApplication.class, TestJpaDbConfig.class})
@AutoConfigureMockMvc
public abstract class AbstractTest {

	@Autowired
	protected UserRepo userRepo;
	
	@Autowired
	protected MockMvc mockMvc;

	@BeforeEach
	public final void initTest() {
		assertNotNull(mockMvc);
		assertNotNull(userRepo);		
		userRepo.deleteAll();
	}
	
	protected String mapToJson(Object object) {
		try {
			ObjectMapper jsonmapper = new ObjectMapper();
			return jsonmapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	protected RequestPostProcessor givenAuthentication(String email, String password) {
		return SecurityMockMvcRequestPostProcessors.httpBasic(email, password);
	}
}
