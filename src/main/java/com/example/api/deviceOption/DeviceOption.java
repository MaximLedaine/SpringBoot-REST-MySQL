package com.example.api.deviceOption;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.api.measurementType.MeasurementType;
import com.example.api.measurementValueType.MeasurementValueType;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "DeviceOption")
public class DeviceOption {
	public interface CreateValidation{}
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@NotEmpty(groups = CreateValidation.class, message = "name may not be empty")
	@Column(nullable = false)
	private String name;

	@ManyToOne(optional=false)
	@NotNull(message = "measurement type may not be null", groups = CreateValidation.class)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MeasurementType measurementType;

	@ManyToMany
	@JsonProperty("valueTypes")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<MeasurementValueType> measurementValueTypes;

	// for deserialisation
	public DeviceOption() {}

	public DeviceOption (
		 String name,
		 MeasurementType measurementType,
		 @JsonProperty("valueTypes") List<MeasurementValueType> measurementValueTypes
	) {
		this.name = name;
		this.measurementType = measurementType;
		this.measurementValueTypes = measurementValueTypes;
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

	public MeasurementType getMeasurementType() {
		return this.measurementType;
	}

	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}

	public List<MeasurementValueType> getMeasurementValueTypes() {
		return this.measurementValueTypes;
	}

	public void setMeasurementValueTypes(List<MeasurementValueType> measurementValueTypes) {
		this.measurementValueTypes = measurementValueTypes;
	}
}