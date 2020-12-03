package app.vehicle.service;

import app.vehicle.model.VehicleModel;

public interface VehicleService {
	public VehicleModel registerVehicle(VehicleModel vehicleModel);
	
	public VehicleModel getVehicle(String vehicleCode);
}
