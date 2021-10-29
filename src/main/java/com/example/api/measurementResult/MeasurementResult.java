package com.example.api.measurementResult;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.example.api.measurementValueType.MeasurementValueType;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "MeasurementResult")
public class MeasurementResult {
	public interface CreateValidation{}
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@NotNull(message = "value may not be null", groups = CreateValidation.class)
	@Column(nullable = false)
	private double value;

	@ManyToOne(optional=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull(message = "value type may not be null", groups = CreateValidation.class)
	@JsonProperty("valueType")
	private MeasurementValueType measurementValueType;

	// for deserialisation
	public MeasurementResult() {}  

	public MeasurementResult(double value, MeasurementValueType measurementValueType) {
		this.value = value;
		this.measurementValueType = measurementValueType;
	}

	public UUID getId() {
		return this.id;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public MeasurementValueType getMeasurementValueType() {
		return this.measurementValueType;
	}

	public void setMeasurementValueType(MeasurementValueType valueType) {
		this.measurementValueType = valueType;
	}
}