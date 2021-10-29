package com.example.api.measurementType;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class MeasurementTypeModelAssembler implements RepresentationModelAssembler<MeasurementType, EntityModel<MeasurementType>> {

  @Override
  public EntityModel<MeasurementType> toModel(MeasurementType measurementType) {

    return EntityModel.of(measurementType, //
        linkTo(methodOn(MeasurementTypeController.class).one(measurementType.getId())).withSelfRel().withType("GET"),
        linkTo(methodOn(MeasurementTypeController.class).all()).withRel("all").withType("GET"),
        linkTo(methodOn(MeasurementTypeController.class).update(measurementType, measurementType.getId())).withRel("update").withType("PUT"),
        linkTo(methodOn(MeasurementTypeController.class).delete(measurementType.getId())).withRel("delete").withType("DELETE")
        );
  }
}
