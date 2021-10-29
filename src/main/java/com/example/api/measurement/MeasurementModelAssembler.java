package com.example.api.measurement;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class MeasurementModelAssembler implements RepresentationModelAssembler<Measurement, EntityModel<Measurement>> {

  @Override
  public EntityModel<Measurement> toModel(Measurement measurement) {

    EntityModel<Measurement> measurementModel =  EntityModel.of(measurement, //
        linkTo(methodOn(MeasurementController.class).one(measurement.getId())).withSelfRel().withType("GET"),
        linkTo(methodOn(MeasurementController.class).all(null)).withRel("all").withType("GET"),
        linkTo(methodOn(MeasurementController.class).save(measurement)).withRel("create").withType("POST")
      );

    if(DateUtils.isSameDay(new Date(), measurement.getDate()))
      measurementModel.add(linkTo(methodOn(MeasurementController.class).update(measurement, measurement.getId())).withRel("update").withType("PUT"));

    return measurementModel;
  }
}
