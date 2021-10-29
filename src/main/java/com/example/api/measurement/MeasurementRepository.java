package com.example.api.measurement;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

import com.example.api.device.Device;
import com.example.api.location.Location;
import com.example.api.measurementType.MeasurementType;

import org.springframework.data.repository.CrudRepository;

// import com.example.accessingdatamysql.Greeting;

// This will be AUTO IMPLEMENTED by Spring into a Bean called greetingRepository
// CRUD refers Create, Read, Update, Delete

public interface MeasurementRepository extends CrudRepository<Measurement, UUID> {
  List<Measurement> findByLocationIn(List<Location> locations);
  Measurement findByDeviceAndLocationAndMeasurementTypeAndDateAfterAndDateBefore(Device device, Location location, MeasurementType measurementType, Timestamp daybefore, Timestamp dayafter);
}