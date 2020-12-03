package app.dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "VEHICLE")
@Getter
@Setter
public class Vehicle extends AbstractEntity {

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VEHICLE_CODE")
	private String vehicleCode;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = true)
	private User owner;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "vehicle", fetch = FetchType.LAZY)
	private List<Maintenance> maintenances;
}
