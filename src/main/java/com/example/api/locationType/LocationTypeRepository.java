package com.example.api.locationType;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

// import com.example.api.Greeting;

// This will be AUTO IMPLEMENTED by Spring into a Bean called greetingRepository
// CRUD refers Create, Read, Update, Delete

public interface LocationTypeRepository extends CrudRepository<LocationType, UUID> {

}