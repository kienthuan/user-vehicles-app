package test.api;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import app.vehicle.model.VehicleModel;
import test.AbstractTest;
import test.TestDataFactory;

public class TestVehicleController extends AbstractTest {

	@Test
	public void testRegisterVehicle_With_AllData_ShouldOk() throws Exception {
		VehicleModel vehicleRequest = TestDataFactory.buildVehicleModel();
		ResultActions registerResult = this.mockMvc.perform(post("/vehicle/register")
				.content(objectToJson(vehicleRequest))
				.contentType(MediaType.APPLICATION_JSON));
		registerResult.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated());
		
		VehicleModel registerResponse = this.extractResponseAsObject(registerResult, VehicleModel.class);
		assertNotNull(registerResponse);
		assertNotNull(registerResponse.getId());
	}
}
