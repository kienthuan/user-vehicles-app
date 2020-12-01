package test.api;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import app.user.model.UserModel;
import app.user.model.UserVehicleList;
import app.vehicle.model.VehicleModel;
import test.AbstractTest;
import test.TestDataFactory;

public class TestUserController extends AbstractTest {

	@Test
	public void testRegisterUser_With_AllData_ShouldOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		UserModel registerResponse = this.extractResponseAsObject(registerResult, UserModel.class);
		assertNotNull(registerResponse);
		assertNotNull(registerResponse.getId());
	}
	
	@Test
	public void testRegisterUser_With_MissData_ShouldNOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		userRequest.setFirstName(null);
		userRequest.setLastName(null);
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testRegisterUser_With_InvalidEmail_ShouldNOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		userRequest.setEmail("invalid.email");
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testRegisterUser_With_ExistedEmail_ShouldOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		registerResult = this.mockMvc.perform(post("/user/register")
					.content(objectToJson(userRequest))
					.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testRegisterVehicle_For_User_ShouldOk() throws Exception {
		//register user
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		UserModel registerResponse = this.extractResponseAsObject(registerResult, UserModel.class);
		assertNotNull(registerResponse);
		assertNotNull(registerResponse.getId());
		
		//register vehicle
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions vehicleResult = this.mockMvc.perform(post("/vehicle/register")
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		vehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel vehicleResponse = this.extractResponseAsObject(vehicleResult, VehicleModel.class);
		assertNotNull(vehicleResponse);
		assertNotNull(vehicleResponse.getId());
		
		//add vehicle for user
		UserVehicleList vehiclesList = new UserVehicleList();
		vehiclesList.add(vehicleResponse.getId());
		
		ResultActions addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(userRequest.getEmail(), userRequest.getPassword()))
				.content(objectToJson(vehiclesList))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
	}
}
