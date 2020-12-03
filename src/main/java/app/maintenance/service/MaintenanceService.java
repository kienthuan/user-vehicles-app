package app.maintenance.service;

import app.maintenance.model.MaintenanceModel;

public interface MaintenanceService {
	public MaintenanceModel addMaintenance(String vehicleId, MaintenanceModel model);
	public MaintenanceModel getMaintenance(String maintenanceId);
	public MaintenanceModel editMaintenance(String maintenanceId, MaintenanceModel model);
	public String deleteMaintenance(String maintenanceId);
}
