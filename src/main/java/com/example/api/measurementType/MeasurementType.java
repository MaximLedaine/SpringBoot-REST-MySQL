package com.example.api.measurementType;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;




@Entity // This tells Hibernate to make a table out of this class
@Table(name = "MeasurementType")
public class MeasurementType {
	public interface CreateValidation {}
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@NotEmpty(groups = CreateValidation.class, message = "name may not be empty")
	@Column(nullable = false)
	private String name;

	// for deserialisation
	public MeasurementType() {}  

	public MeasurementType(String name) {
		this.name = name;
	}

	public UUID getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}