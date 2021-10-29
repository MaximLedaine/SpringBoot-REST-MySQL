package com.example.api.person;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Person")
public class Person {
	interface CreateValidation {}
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@NotEmpty(groups = CreateValidation.class, message = "name may not be empty")
	@Column(nullable = false)
	private String firstName;

	@NotEmpty(groups = CreateValidation.class, message = "name may not be empty")
	@Column(nullable = false)
	private String lastName;

	@NotEmpty(groups = CreateValidation.class, message = "email may not be empty")
	@Email(message = "email must have email format")
	@Column(nullable = false)
	private String email;

	@NotEmpty(groups = CreateValidation.class, message = "telephone may not be empty")
	@Column(nullable = false)
	private String telephone;

	// for deserialisation
	public Person() {}  

	public Person(String firstName, String lastName, String email, String telephone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telephone= telephone;
	}

	public UUID getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}