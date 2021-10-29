package com.example.api.measurement;
import com.example.api.device.Device;
import com.example.api.location.*;
import com.example.api.measurementResult.MeasurementResult;
import com.example.api.measurementType.MeasurementType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Measurement")
public class Measurement {
	public interface CreateValidation {}
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@NotEmpty(groups = CreateValidation.class, message = "name may not be empty")
	@Column(nullable = false)
	private String name;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Timestamp date;

	@ManyToOne(optional=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull(message = "location may not be null", groups = CreateValidation.class)
	private Location location;

	@ManyToOne()
	// @OnDelete(action = OnDeleteAction.SETNULL)
	@NotNull(message = "device may not be null", groups = CreateValidation.class)
	private Device device;

	@ManyToOne(optional=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull(message = "measurement type may not be null", groups = CreateValidation.class)
	private MeasurementType measurementType;

	@JsonProperty("results")
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@NotNull(message = "results may not be null", groups = CreateValidation.class)
	@JoinColumn(name = "measurement_id")
	private List<MeasurementResult> measurementResults;

	// for deserialisation
	public Measurement() {}  

	public Measurement(String name, Timestamp date, MeasurementType measurementType, Device device, List<MeasurementResult> results, Location location) {
		this.name = name;
		this.setDate(date);
		this.device = device;
		this.measurementType = measurementType;
		this.measurementResults = results;
		this.location = location;
	}

	public UUID getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation (Location location) {
		this.location = location;
	}

	public List<MeasurementResult> getMeasurementResults() {
		return this.measurementResults;
	}

	public void setMeasurementResults (List<MeasurementResult> results) {
		this.measurementResults = results;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice (Device device) {
		this.device = device;
	}

	public MeasurementType getMeasurementType() {
		return this.measurementType;
	}

	public void setMeasurementType (MeasurementType measurementType) {
		this.measurementType = measurementType;
	}
}