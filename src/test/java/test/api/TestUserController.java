package test.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import app.user.model.UserModel;
import test.AbstractTest;
import test.TestDataFactory;

public class TestUserController extends AbstractTest {

	@Test
	public void testRegisterUser_With_AllData_ShouldOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(mapToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		ResultActions getResult = this.mockMvc.perform(get("/user")
				.with(givenAuthentication(userRequest.getEmail(), userRequest.getPassword())));
		getResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
	}
	
	@Test
	public void testRegisterUser_With_MissData_ShouldNOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		userRequest.setFirstName(null);
		userRequest.setLastName(null);
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(mapToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testRegisterUser_With_InvalidEmail_ShouldNOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		userRequest.setEmail("invalid.email");
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(mapToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testRegisterUser_With_ExistedEmail_ShouldOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(mapToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		registerResult = this.mockMvc.perform(post("/user/register")
					.content(mapToJson(userRequest))
					.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
}
