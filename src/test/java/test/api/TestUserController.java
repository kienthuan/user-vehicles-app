package test.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

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
		registerResult.andExpect(status().isCreated());
	}
}
