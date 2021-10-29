package com.example.api.measurementValueType;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

// import com.example.api.Greeting;

// This will be AUTO IMPLEMENTED by Spring into a Bean called greetingRepository
// CRUD refers Create, Read, Update, Delete

public interface MeasurementValueTypeRepository extends CrudRepository<MeasurementValueType, UUID> {

}