package test.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import app.maintenance.model.MaintenanceModel;
import app.vehicle.model.VehicleModel;
import test.AbstractTest;
import test.TestDataFactory;

public class TestVehicleController extends AbstractTest {

	@Test
	public void testRegisterVehicle_With_AllData_ThenGet_ShouldOk() throws Exception {
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions registerResult = this.mockMvc.perform(post("/vehicle/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel registerResponse = this.extractResponseAsObject(registerResult, VehicleModel.class);
		assertNotNull(registerResponse);
		assertNotNull(registerResponse.getId());
		
		ResultActions getResult = this.mockMvc.perform(get("/vehicle/" + registerResponse.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD)));
		getResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		VehicleModel getResponse = this.extractResponseAsObject(getResult, VehicleModel.class);
		assertNotNull(getResponse);
		assertEquals(registerResponse.getId(), getResponse.getId());
		assertEquals(registerResponse.getName(), getResponse.getName());
		assertEquals("", getResponse.getOwner());
	}
	
	@Test
	public void testAddMaintenance_ThenGet_ShouldOk() throws Exception {
		//register vehicle
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions registerResult = this.mockMvc.perform(post("/vehicle/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel regVehicleResp = this.extractResponseAsObject(registerResult, VehicleModel.class);
		assertNotNull(regVehicleResp);
		assertNotNull(regVehicleResp.getId());
		
		//add maintenance
		MaintenanceModel maintenanceRequest = new MaintenanceModel();
		maintenanceRequest.setCost(BigDecimal.TEN);
		maintenanceRequest.setComment("First repair");
		
		ResultActions addResult = this.mockMvc.perform(post("/maintenance/" + regVehicleResp.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(maintenanceRequest))
				.contentType(MediaType.APPLICATION_JSON));
		addResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		MaintenanceModel addResponse = this.extractResponseAsObject(addResult, MaintenanceModel.class);
		assertNotNull(addResponse);
		assertNotNull(addResponse.getId());
		assertEquals(regVehicleResp.getId(), addResponse.getVehicleId());
		
		//get vehicle
		ResultActions getResult = this.mockMvc.perform(get("/vehicle/" + regVehicleResp.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD)));
		getResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		VehicleModel getResponse = this.extractResponseAsObject(getResult, VehicleModel.class);
		assertNotNull(getResponse);
		assertEquals(regVehicleResp.getId(), getResponse.getId());
		assertEquals(regVehicleResp.getName(), getResponse.getName());
		assertNotNull(getResponse.getMaintenanceHistory());
		assertFalse(getResponse.getMaintenanceHistory().isEmpty());
		assertNotNull(getResponse.getMaintenanceHistory().get(0).getId());
	}
	
	@Test
	public void testAddMaintenance_With_NotExistedVehicle_ShouldBadRequest() throws Exception {
		//add maintenance
		MaintenanceModel maintenanceRequest = new MaintenanceModel();
		maintenanceRequest.setCost(BigDecimal.TEN);
		maintenanceRequest.setComment("First repair");
		
		ResultActions addResult = this.mockMvc.perform(post("/maintenance/not-existed-vehicle")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(maintenanceRequest))
				.contentType(MediaType.APPLICATION_JSON));
		addResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testEditMaintenance_ThenGet_ShouldOk() throws Exception {
		//register vehicle
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions registerResult = this.mockMvc.perform(post("/vehicle/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel regVehicleResp = this.extractResponseAsObject(registerResult, VehicleModel.class);
		assertNotNull(regVehicleResp);
		assertNotNull(regVehicleResp.getId());
		
		//add maintenance
		MaintenanceModel maintenanceRequest = new MaintenanceModel();
		maintenanceRequest.setCost(BigDecimal.TEN);
		maintenanceRequest.setComment("First repair");
		
		ResultActions addResult = this.mockMvc.perform(post("/maintenance/" + regVehicleResp.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(maintenanceRequest))
				.contentType(MediaType.APPLICATION_JSON));
		addResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		MaintenanceModel addResponse = this.extractResponseAsObject(addResult, MaintenanceModel.class);
		assertNotNull(addResponse);
		assertNotNull(addResponse.getId());
		assertEquals(regVehicleResp.getId(), addResponse.getVehicleId());
		
		//get vehicle
		ResultActions getResult = this.mockMvc.perform(get("/vehicle/" + regVehicleResp.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD)));
		getResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		VehicleModel getResponse = this.extractResponseAsObject(getResult, VehicleModel.class);
		assertNotNull(getResponse);
		assertEquals(regVehicleResp.getId(), getResponse.getId());
		assertEquals(regVehicleResp.getName(), getResponse.getName());
		assertNotNull(getResponse.getMaintenanceHistory());
		assertFalse(getResponse.getMaintenanceHistory().isEmpty());
		assertNotNull(getResponse.getMaintenanceHistory().get(0).getId());
		
		//edit maintenance record
		maintenanceRequest.setCost(BigDecimal.valueOf(100));
		maintenanceRequest.setComment("First repair after edited");
		
		ResultActions editResult = this.mockMvc.perform(put("/maintenance/" + addResponse.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(maintenanceRequest))
				.contentType(MediaType.APPLICATION_JSON));
		editResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		MaintenanceModel editResponse = this.extractResponseAsObject(editResult, MaintenanceModel.class);
		assertNotNull(editResponse);
		assertNotNull(editResponse.getId());
		assertEquals(addResponse.getId(), editResponse.getId());
		assertEquals(maintenanceRequest.getCost(), editResponse.getCost());
		assertEquals(maintenanceRequest.getComment(), editResponse.getComment());
	}
	
	@Test
	public void testEditMaintenance_With_NotExistedRecored_ShouldBadRequest() throws Exception {		
		//edit maintenance record
		MaintenanceModel maintenanceRequest = new MaintenanceModel();
		maintenanceRequest.setCost(BigDecimal.TEN);
		maintenanceRequest.setComment("First repair");
		
		ResultActions editResult = this.mockMvc.perform(put("/maintenance/not-exisied-record")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(maintenanceRequest))
				.contentType(MediaType.APPLICATION_JSON));
		editResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testDeleteMaintenance_ThenGet_ShouldOk() throws Exception {
		//register vehicle
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions registerResult = this.mockMvc.perform(post("/vehicle/register")
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel regVehicleResp = this.extractResponseAsObject(registerResult, VehicleModel.class);
		assertNotNull(regVehicleResp);
		assertNotNull(regVehicleResp.getId());
		
		//add maintenance
		MaintenanceModel maintenanceRequest = new MaintenanceModel();
		maintenanceRequest.setCost(BigDecimal.TEN);
		maintenanceRequest.setComment("First repair");
		
		ResultActions addResult = this.mockMvc.perform(post("/maintenance/" + regVehicleResp.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD))
				.content(objectToJson(maintenanceRequest))
				.contentType(MediaType.APPLICATION_JSON));
		addResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		MaintenanceModel addResponse = this.extractResponseAsObject(addResult, MaintenanceModel.class);
		assertNotNull(addResponse);
		assertNotNull(addResponse.getId());
		assertEquals(regVehicleResp.getId(), addResponse.getVehicleId());
		
		//delete maintenance record		
		ResultActions deleteResult = this.mockMvc.perform(delete("/maintenance/" + addResponse.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD)));
		deleteResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		//get vehicle
		ResultActions getResult = this.mockMvc.perform(get("/vehicle/" + regVehicleResp.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD)));
		getResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		VehicleModel getResponse = this.extractResponseAsObject(getResult, VehicleModel.class);
		assertNotNull(getResponse);
		assertEquals(regVehicleResp.getId(), getResponse.getId());
		assertEquals(regVehicleResp.getName(), getResponse.getName());
		assertNotNull(getResponse.getMaintenanceHistory());
		assertTrue(getResponse.getMaintenanceHistory().isEmpty());
		
		//get maintenance record
		ResultActions getMaintenanceResult = this.mockMvc.perform(get("/maintenance/" + addResponse.getId())
				.with(givenAuthentication(ADMIN_EMAIL, ADMIN_PASSWORD)));
		getMaintenanceResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());
	}
}
