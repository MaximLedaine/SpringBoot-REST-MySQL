package com.example.api.measurementValueType;

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

import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MeasurementValueTypeController {
	@Autowired // This means to get the bean called MeasurementRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
  private MeasurementValueTypeRepository repository;
  
  @Autowired 
  private MeasurementValueTypeModelAssembler assembler;

  /**
   * Get measurementValueTypes.
   *
   * @param measurementValueType the measurementValueType
   * @return the measurementValueType
   */
  @GetMapping(path="/measurement-value-types")
  CollectionModel<EntityModel<MeasurementValueType>> all() {

    List<EntityModel<MeasurementValueType>> measurementTypes = StreamSupport.stream(repository.findAll().spliterator(), false)
          .map(assembler::toModel) //
          .collect(Collectors.toList());
  
    return CollectionModel.of(measurementTypes, 
    linkTo(methodOn(MeasurementValueTypeController.class).all()).withSelfRel().withType("GET"),
    linkTo(methodOn(MeasurementValueTypeController.class).save(new MeasurementValueType())).withRel("create").withType("POST")
    );
  }
  
   /**
   * Create measurementValueType.
   *
   * @param measurementValueType the measurementValueType
   * @return the measurementValueType
   */
  @PostMapping(path="/measurement-value-types")
  ResponseEntity<?> save(@RequestBody @Validated(MeasurementValueType.CreateValidation.class) MeasurementValueType newMeasurementValueType) {
  
    MeasurementValueType measurementValueType = repository.save(newMeasurementValueType);
    EntityModel<MeasurementValueType> entityModel = assembler.toModel(measurementValueType);
  
    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  /**
   * create or update measurementValueType.
   *
   * @param measurementValueType the measurementValueType
   * @return the measurementValueType
   */
  @PutMapping("/measurement-value-types/{id}")
  ResponseEntity<?> update(@RequestBody @Validated(MeasurementValueType.CreateValidation.class) MeasurementValueType newMeasurementValueType, @PathVariable UUID id) {

    MeasurementValueType updatedMeasurement = repository.findById(id)
    .map(measurementValueType -> {
      measurementValueType.setName(newMeasurementValueType.getName());
      return repository.save(measurementValueType);
    })
    .orElseGet(() -> {
      return repository.save(newMeasurementValueType);
    });

  EntityModel<MeasurementValueType> entityModel = assembler.toModel(updatedMeasurement);

  return ResponseEntity //
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
      .body(entityModel);
  }

  /**
   * Get measurementValueType.
   *
   * @param measurementValueType the measurementValueType
   * @return the measurementValueType
   */
  @GetMapping(path="/measurement-value-types/{id}")
  public EntityModel<MeasurementValueType> one(@PathVariable(value = "id") UUID id) throws EntityNotFoundException {
    MeasurementValueType measurementValueType = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

    return assembler.toModel(measurementValueType);
  }

  /**
   * Delete measurementValueType.
   *
   * @param measurementValueType the measurementValueType
   * @return the measurementValueType
   */
  @DeleteMapping("/measurement-value-types/{id}")
  ResponseEntity<?> delete(@PathVariable(value = "id") UUID id) throws EntityNotFoundException {
    MeasurementValueType measurementValueType = repository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException());

    repository.delete(measurementValueType);

    return ResponseEntity.noContent().build();
  }
}
