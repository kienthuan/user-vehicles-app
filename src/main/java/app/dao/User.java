package app.dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User extends AbstractEntity {
	
	@Column(name = "USER_CODE")
	private String userCode;

	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private UserRoleEnum role;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "owner", fetch = FetchType.LAZY)
	private List<Vehicle> vehicles;
}
