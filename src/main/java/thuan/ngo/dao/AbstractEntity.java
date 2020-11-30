package thuan.ngo.dao;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	protected Long id;

	@Column(name = "CREATED_AT")
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
	protected LocalDateTime creationTimestamp;

	@Column(name = "MODIFIED_AT")
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
	protected LocalDateTime updateTimestamp;
	
    protected AbstractEntity() {
    	creationTimestamp = LocalDateTime.now();
    	updateTimestamp = LocalDateTime.now();
    }
}
