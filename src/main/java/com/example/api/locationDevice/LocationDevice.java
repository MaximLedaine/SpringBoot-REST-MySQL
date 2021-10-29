package com.example.api.locationDevice;
import com.example.api.device.Device;
import com.example.api.location.Location;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Location_Device")
public class LocationDevice {
	public interface CreateValidation{}
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@NotNull(message = "installation date may not be null", groups = CreateValidation.class)
	@Column(nullable = false)
	private Timestamp installationDate;

	private Timestamp removalDate;

	@ManyToOne(optional=false)
	@NotNull(message = "device may not be null", groups = CreateValidation.class)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Device device;

	@ManyToOne(optional=false)
	@NotNull(message = "location may not be null", groups = CreateValidation.class)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Location location;

	// private Boolean active;
	
	// for deserialisation
	public LocationDevice() {}  

	public LocationDevice(Location location, Device device) {
		this.installationDate = new Timestamp(new Date().getTime());
		this.removalDate = null;
		this.location = location;
		this.device = device;
	}

	public LocationDevice(Timestamp installationDate, Location location, Device device) {
		this.installationDate = installationDate;
		this.removalDate = null;
		this.location = location;
		this.device = device;
	}

	public UUID getId() {
		return this.id;
	}

	public Timestamp getInstallationDate() {
		return this.installationDate;
	}

	public void setInstallationDate(Timestamp installationDate) {
		this.installationDate = installationDate;
	}

	public Timestamp getRemovalDate() {
		return this.removalDate;
	}

	public void setRemovalDate(Timestamp removalDate) {
		this.removalDate = removalDate;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}