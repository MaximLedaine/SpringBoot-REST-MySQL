package com.example.api.person;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

// import com.example.accessingdatamysql.Greeting;

// This will be AUTO IMPLEMENTED by Spring into a Bean called greetingRepository
// CRUD refers Create, Read, Update, Delete

public interface PersonRepository extends CrudRepository<Person, UUID> {

}