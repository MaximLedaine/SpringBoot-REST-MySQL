package com.example.api.locationDevice;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.example.api.device.Device;
import com.example.api.location.Location;

import org.springframework.data.repository.CrudRepository;

// import com.example.accessingdatamysql.Greeting;

// This will be AUTO IMPLEMENTED by Spring into a Bean called greetingRepository
// CRUD refers Create, Read, Update, Delete

public interface LocationDeviceRepository extends CrudRepository<LocationDevice, UUID> {
  // https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
  List<LocationDevice> findByLocationAndRemovalDate (Location location, Timestamp timestamp);
  LocationDevice findByDeviceAndRemovalDate (Device device, Timestamp timestamp);
}