package com.example.api.location;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.api.locationType.LocationType;
import com.example.api.person.Person;

import org.hibernate.annotations.Type;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Location")
public class Location {
	public interface CreateValidation {}
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@NotEmpty(groups = CreateValidation.class, message = "name may not be empty")
	@Column(nullable = false)
	private String name;
	
	@NotEmpty(groups = CreateValidation.class, message = "street may not be empty")
	@Column(nullable = false)
	private String street;

	@NotEmpty(groups = CreateValidation.class, message = "zipCode may not be empty")
	@Column(nullable = false)
	private String zipCode;

	@NotEmpty(groups = CreateValidation.class, message = "city may not be empty")
	@Column(nullable = false)
	private String city;

	@NotEmpty(groups = CreateValidation.class, message = "country may not be empty")
	@Column(nullable = false)
	private String country;

	@ManyToOne(optional=true)
	@NotNull(message = "location type may not be null", groups = CreateValidation.class)
	// @OnDelete(action = OnDeleteAction.SETNULL)
	private LocationType locationType;

	@ManyToOne(optional=true)
	// @OnDelete(action = OnDeleteAction.SETNULL)
	private Person contactPerson;
	
	// for deserialisation
	public Location() {}  

	public Location(String name, String street, String zipCode, String city, String country,  LocationType locationType, Person contactPerson) {
		this.name = name;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
		this.locationType = locationType;
		this.contactPerson = contactPerson;
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

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public LocationType getLocationType() {
		return this.locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType= locationType;
	}

	public Person getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson (Person person) {
		this.contactPerson = person;
	}
}