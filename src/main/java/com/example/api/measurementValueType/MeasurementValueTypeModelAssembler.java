package com.example.api.measurementValueType;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class MeasurementValueTypeModelAssembler implements RepresentationModelAssembler<MeasurementValueType, EntityModel<MeasurementValueType>> {

  @Override
  public EntityModel<MeasurementValueType> toModel(MeasurementValueType measurementValueType) {

    return EntityModel.of(measurementValueType, //
        linkTo(methodOn(MeasurementValueTypeController.class).one(measurementValueType.getId())).withSelfRel().withType("GET"),
        linkTo(methodOn(MeasurementValueTypeController.class).all()).withRel("all").withType("GET"),
        linkTo(methodOn(MeasurementValueTypeController.class).update(measurementValueType, measurementValueType.getId())).withRel("update").withType("PUT"),
        linkTo(methodOn(MeasurementValueTypeController.class).delete(measurementValueType.getId())).withRel("delete").withType("DELETE")
        );
  }
}
