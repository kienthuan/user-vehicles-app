package test.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import app.user.model.UserModel;
import app.user.model.AddVehicleList;
import app.user.model.ChangeVehicleOwnerModel;
import app.vehicle.model.VehicleModel;
import test.AbstractTest;
import test.TestDataFactory;

public class TestUserController extends AbstractTest {

	@Test
	public void testRegisterUser_With_AllData_ShouldOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		UserModel registerResponse = this.extractResponseAsObject(registerResult, UserModel.class);
		assertNotNull(registerResponse);
		assertNotNull(registerResponse.getId());
		
		//get user with new user
		ResultActions getResult = this.mockMvc.perform(get("/user")
				.with(givenAuthentication(userRequest.getEmail(), userRequest.getPassword())));
		getResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		UserModel getResponse = this.extractResponseAsObject(getResult, UserModel.class);
		assertNotNull(getResponse);
		assertEquals(registerResponse.getId(), getResponse.getId());
	}
	
	@Test
	public void testRegisterUser_With_MissData_ShouldNOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		userRequest.setFirstName(null);
		userRequest.setLastName(null);
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testRegisterUser_With_InvalidEmail_ShouldNOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		userRequest.setEmail("invalid.email");
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testRegisterUser_With_ExistedEmail_ShouldOk() throws Exception {
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		registerResult = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
					.content(objectToJson(userRequest))
					.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void tesAddVehicleToUser_ShouldOk() throws Exception {
		//register user
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		UserModel regUserResp = this.extractResponseAsObject(registerResult, UserModel.class);
		assertNotNull(regUserResp);
		assertNotNull(regUserResp.getId());
		
		//register vehicle
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions regVehicleResult = this.mockMvc.perform(post("/vehicle/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		regVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel regVehicleResp = this.extractResponseAsObject(regVehicleResult, VehicleModel.class);
		assertNotNull(regVehicleResp);
		assertNotNull(regVehicleResp.getId());
		
		//add vehicle for user
		AddVehicleList vehiclesList = new AddVehicleList();
		vehiclesList.put(regUserResp.getId(), Arrays.asList(regVehicleResp.getId()));
		
		ResultActions addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehiclesList))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		List<UserModel> addVehiclesResp = this.extractResponseAsObject(addVehicleResult, ArrayList.class);
		assertNotNull(addVehiclesResp);
		assertNotNull(addVehiclesResp.get(0));
		
		//get user with new user
		ResultActions getResult = this.mockMvc.perform(get("/user")
				.with(givenAuthentication(userRequest.getEmail(), userRequest.getPassword())));
		getResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		UserModel getResponse = this.extractResponseAsObject(getResult, UserModel.class);
		assertNotNull(getResponse);
		assertEquals(regUserResp.getId(), getResponse.getId());
		assertFalse(getResponse.getVehicleList().isEmpty());
	}
	
	@Test
	public void tesAddVehicleToUser_With_NotExistedUser_ShouldBadrequest() throws Exception {		
		//register vehicle
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions regVehicleResult = this.mockMvc.perform(post("/vehicle/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		regVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel regVehicleResp = this.extractResponseAsObject(regVehicleResult, VehicleModel.class);
		assertNotNull(regVehicleResp);
		assertNotNull(regVehicleResp.getId());
		
		//add vehicle for user
		AddVehicleList vehiclesList = new AddVehicleList();
		vehiclesList.put("not-found-user", Arrays.asList(regVehicleResp.getId()));
		
		ResultActions addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehiclesList))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void tesAddVehicleToUser_With_NotExistedVehicle_ShouldBadrequest() throws Exception {
		//register user
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		UserModel regUserResp = this.extractResponseAsObject(registerResult, UserModel.class);
		assertNotNull(regUserResp);
		assertNotNull(regUserResp.getId());
		
		//add vehicle for user
		AddVehicleList vehiclesList = new AddVehicleList();
		vehiclesList.put(regUserResp.getId(), Arrays.asList("not-found-vehicle"));
		
		ResultActions addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehiclesList))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void tesAddVehicleToUser_WithAlreadyAssignVehicle_ShouldBadRequest() throws Exception {
		//register user
		UserModel userRequest = TestDataFactory.buildUserModel();
		ResultActions registerResult = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(userRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		UserModel regUserResp = this.extractResponseAsObject(registerResult, UserModel.class);
		assertNotNull(regUserResp);
		assertNotNull(regUserResp.getId());
		
		//register vehicle
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions regVehicleResult = this.mockMvc.perform(post("/vehicle/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		regVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel regVehicleResp = this.extractResponseAsObject(regVehicleResult, VehicleModel.class);
		assertNotNull(regVehicleResp);
		assertNotNull(regVehicleResp.getId());
		
		//add vehicle for user
		AddVehicleList vehiclesList = new AddVehicleList();
		vehiclesList.put(regUserResp.getId(), Arrays.asList(regVehicleResp.getId()));
		
		ResultActions addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehiclesList))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		List<UserModel> addVehiclesResp = this.extractResponseAsObject(addVehicleResult, ArrayList.class);
		assertNotNull(addVehiclesResp);
		assertNotNull(addVehiclesResp.get(0));
		
		//add same vehicle
		addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehiclesList))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void tesAddVehicleToUser_With_EmptyBody_ShouldBadRequest() throws Exception {		
		//add vehicle for user		
		ResultActions addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void tesAddVehicleToUser_With_EmptyList_ShouldBadRequest() throws Exception {		
		//add vehicle for user		
		ResultActions addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(new AddVehicleList()))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void teChangeVehicleOwner_ShouldOk() throws Exception {
		//register 1st user
		UserModel userRequest_1 = TestDataFactory.buildUserModel();
		ResultActions regUserResult_1 = this.mockMvc.perform(post("/user/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(userRequest_1))
				.contentType(MediaType.APPLICATION_JSON));
		regUserResult_1.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		UserModel regUserResp_1 = this.extractResponseAsObject(regUserResult_1, UserModel.class);
		assertNotNull(regUserResp_1);
		assertNotNull(regUserResp_1.getId());
		
		//register 2nd user
		UserModel userRequest_2 = TestDataFactory.buildUserModel("2nd User", "2nd User", "2nd.user@gamil.com", "2nduser123");
		ResultActions regUserResult_2 = this.mockMvc
				.perform(post("/user/register").with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
						.content(objectToJson(userRequest_2)).contentType(MediaType.APPLICATION_JSON));
		regUserResult_2.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());

		UserModel regUserResp_2 = this.extractResponseAsObject(regUserResult_2, UserModel.class);
		assertNotNull(regUserResp_2);
		assertNotNull(regUserResp_2.getId());
		
		//register vehicle
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions regVehicleResult = this.mockMvc.perform(post("/vehicle/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		regVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel regVehicleResp = this.extractResponseAsObject(regVehicleResult, VehicleModel.class);
		assertNotNull(regVehicleResp);
		assertNotNull(regVehicleResp.getId());
		
		//add vehicle for 1st user
		AddVehicleList vehiclesList = new AddVehicleList();
		vehiclesList.put(regUserResp_1.getId(), Arrays.asList(regVehicleResp.getId()));
		
		ResultActions addVehicleResult = this.mockMvc.perform(patch("/user/vehicles")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehiclesList))
				.contentType(MediaType.APPLICATION_JSON));
		addVehicleResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		List<UserModel> addVehiclesResp = this.extractResponseAsObject(addVehicleResult, ArrayList.class);
		assertNotNull(addVehiclesResp);
		assertNotNull(addVehiclesResp.get(0));
		
		//Change owner ship
		ChangeVehicleOwnerModel changeRequest = new ChangeVehicleOwnerModel();
		changeRequest.setFromOwnerId(regUserResp_1.getId());
		changeRequest.setToOwnerId(regUserResp_2.getId());
		changeRequest.setVehiclesList(Arrays.asList(regVehicleResp.getId()));
		
		ResultActions changeOwnerResult = this.mockMvc.perform(put("/user/vehicles/owner")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(changeRequest))
				.contentType(MediaType.APPLICATION_JSON));
		changeOwnerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		UserModel changeOwnerResp = this.extractResponseAsObject(changeOwnerResult, UserModel.class);
		assertNotNull(changeOwnerResp);
		assertEquals(regUserResp_2.getId(), changeOwnerResp.getId());
		
		//get 1st user should not have any vehicles
		ResultActions getResult = this.mockMvc.perform(get("/user")
				.with(givenAuthentication(userRequest_1.getEmail(), userRequest_1.getPassword())));
		getResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		UserModel getResponse = this.extractResponseAsObject(getResult, UserModel.class);
		assertNotNull(getResponse);
		assertEquals(regUserResp_1.getId(), getResponse.getId());
		assertTrue(getResponse.getVehicleList().isEmpty());
	}
}
