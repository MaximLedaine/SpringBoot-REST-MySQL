package com.example.api.measurement;


import com.example.api.error.BadRequest;
import com.example.api.location.Location;
import com.example.api.location.LocationRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MeasurementController {
	@Autowired // This means to get the bean called MeasurementRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
  private MeasurementRepository repository;

  @Autowired
  private LocationRepository locationRepository;
  
  @Autowired 
  private MeasurementModelAssembler assembler;

  /**
   * Get measurements.
   *
   * @param measurement the measurement
   * @return the measurement
   */
  @GetMapping(path="/measurements")
  CollectionModel<EntityModel<Measurement>> all(@RequestParam(value = "locationId" , required = false) String locationIds) {
    List<EntityModel<Measurement>> measurements;
    if(locationIds == null) {
      measurements = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(assembler::toModel) //
                .collect(Collectors.toList());
    } else {
        // something like this
        // look at the locationDeviceRepository and visit the link
        Iterable<Location> locations;

        String[] locationIdArray = locationIds.split(",");
        ArrayList<UUID> ids = new ArrayList<>();
        
        for(String s : locationIdArray) {
          ids.add(UUID.fromString(s));  
        }
        Iterable<UUID> iterableIds = ids;

        locations = locationRepository.findAllById(iterableIds);

        List<Location> ls = new ArrayList<>();
        for(Location l : locations)
          ls.add(l);

        List<Measurement> measurementsByLocation = repository.findByLocationIn(ls);
        measurements = StreamSupport.stream(measurementsByLocation.spliterator(), false)
                .map(assembler::toModel) //
                .collect(Collectors.toList());
    }

  
    return CollectionModel.of(measurements, 
    linkTo(methodOn(MeasurementController.class).all(null)).withSelfRel().withType("GET"),
    linkTo(methodOn(MeasurementController.class).save(new Measurement())).withRel("create").withType("POST")
    );
  }
  
   /**
   * Create measurement.
   *
   * @param measurement the measurement
   * @return the measurement
   */
  @PostMapping(path="/measurements")
  ResponseEntity<?> save(@RequestBody @Validated(Measurement.CreateValidation.class) Measurement newMeasurement) throws BadRequest {
    LocalDateTime yesterday = newMeasurement.getDate().toLocalDateTime();
    yesterday = yesterday.toLocalDate().atTime(LocalTime.MIN);
    LocalDateTime tomorrow = newMeasurement.getDate().toLocalDateTime();
    tomorrow = tomorrow.toLocalDate().atTime(LocalTime.MAX);

    // try to get a measurement for today
    Measurement m = repository.findByDeviceAndLocationAndMeasurementTypeAndDateAfterAndDateBefore(newMeasurement.getDevice(), newMeasurement.getLocation(), newMeasurement.getMeasurementType(), Timestamp.valueOf(yesterday), Timestamp.valueOf(tomorrow));
    if(m != null) throw new BadRequest("Measurement already exists");

    Measurement measurement = repository.save(newMeasurement);
    EntityModel<Measurement> entityModel = assembler.toModel(measurement);
  
    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  /**
   * create or update measurement.
   *
   * @param measurement the measurement
   * @return the measurement
   */
  @PutMapping("/measurements/{id}")
  ResponseEntity<?> update(@RequestBody @Validated(Measurement.CreateValidation.class) Measurement newMeasurement, @PathVariable UUID id) {

    Measurement updatedMeasurement = repository.findById(id)
    .map(measurement -> {
      measurement.setName(newMeasurement.getName());
      measurement.setDate(newMeasurement.getDate());
      measurement.setDevice(newMeasurement.getDevice());
      measurement.setMeasurementType(newMeasurement.getMeasurementType());
      measurement.setMeasurementResults(newMeasurement.getMeasurementResults());
      measurement.setLocation(newMeasurement.getLocation());
      return repository.save(measurement);
    })
    .orElseGet(() -> {
      LocalDateTime yesterday = newMeasurement.getDate().toLocalDateTime();
      yesterday = yesterday.toLocalDate().atTime(LocalTime.MIN);
      LocalDateTime tomorrow = newMeasurement.getDate().toLocalDateTime();
      tomorrow = tomorrow.toLocalDate().atTime(LocalTime.MAX);
       // try to get a measurement for today
      Measurement m = repository.findByDeviceAndLocationAndMeasurementTypeAndDateAfterAndDateBefore(newMeasurement.getDevice(), newMeasurement.getLocation(), newMeasurement.getMeasurementType(), Timestamp.valueOf(yesterday), Timestamp.valueOf(tomorrow));
      if(m != null) throw new BadRequest("Measurement already exists");
      return repository.save(newMeasurement);
    });

  EntityModel<Measurement> entityModel = assembler.toModel(updatedMeasurement);

  return ResponseEntity //
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
      .body(entityModel);
  }

  /**
   * Get measurement.
   *
   * @param measurement the measurement
   * @return the measurement
   */
  @GetMapping(path="/measurements/{id}")
  public EntityModel<Measurement> one(@PathVariable(value = "id") UUID id) throws EntityNotFoundException {
    Measurement measurement = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

    return assembler.toModel(measurement);
  }

  /**
   * Delete measurement.
   *
   * @param measurement the measurement
   * @return the measurement
   */
  @DeleteMapping("/measurements/{id}")
  ResponseEntity<?> delete(@PathVariable(value = "id") UUID id) throws EntityNotFoundException {
    Measurement measurement = repository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException());

    repository.delete(measurement);

    return ResponseEntity.noContent().build();
  }
}
