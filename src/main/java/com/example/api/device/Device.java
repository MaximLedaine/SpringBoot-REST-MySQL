package com.example.api.device;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.example.api.deviceOption.DeviceOption;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import org.hibernate.annotations.Type;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Device")
public class Device {
	public interface CreateValidation{}
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@NotEmpty(groups = CreateValidation.class, message = "name may not be empty")
	@Column(nullable = false)
	private String name;

	@JsonProperty("options")
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "device_id")
	private List<DeviceOption> deviceOptions;

	// for deserialisation
	public Device() {}

	public Device(
		String name,List<DeviceOption> deviceOptions
	) {
		this.name = name;
		this.deviceOptions = deviceOptions;
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

	public List<DeviceOption> getDeviceOptions() {
		return this.deviceOptions;
	}

	public void setDeviceOptions (List<DeviceOption> deviceOptions) {
		this.deviceOptions = deviceOptions;
	}
}