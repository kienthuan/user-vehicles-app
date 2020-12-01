package app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.dao.Vehicle;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
	
	public List<Vehicle> findByVehicleCodeIn(List<String> vehicleCodeList);
}
