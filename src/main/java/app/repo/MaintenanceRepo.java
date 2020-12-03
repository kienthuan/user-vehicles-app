package app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.dao.Maintenance;

public interface MaintenanceRepo extends JpaRepository<Maintenance, Long> {

	public Optional<Maintenance> findByMaintenanceCode(String maintenanceCode);
}
