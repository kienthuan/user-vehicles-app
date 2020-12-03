package app.dao;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "VEHICLE_MAINTENANCE")
@Getter
@Setter
public class Maintenance extends AbstractEntity {
	
	@Column(name = "MAINTENANCE_CODE")
	private String maintenanceCode;

	@Column(name = "COST")
	private BigDecimal cost;
	
	@Column(name = "COMMENT")
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "VEHICLE_ID", nullable = true)
	private Vehicle vehicle;
}
