package com.example.api.location;

import java.util.List;
import java.util.UUID;

import com.example.api.person.Person;
import org.springframework.data.repository.CrudRepository;

// import com.example.accessingdatamysql.Greeting;

// This will be AUTO IMPLEMENTED by Spring into a Bean called greetingRepository
// CRUD refers Create, Read, Update, Delete

public interface LocationRepository extends CrudRepository<Location, UUID> {
    List<Location> findByContactPerson(Person person);


}
