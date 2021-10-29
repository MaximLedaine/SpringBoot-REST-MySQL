package com.example.api.locationType;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class LocationTypeModelAssembler implements RepresentationModelAssembler<LocationType, EntityModel<LocationType>> {

  @Override
  public EntityModel<LocationType> toModel(LocationType locationType) {

    return EntityModel.of(locationType, //
        linkTo(methodOn(LocationTypeController.class).one(locationType.getId())).withSelfRel().withType("GET"),
        linkTo(methodOn(LocationTypeController.class).all()).withRel("all").withType("GET"),
        linkTo(methodOn(LocationTypeController.class).update(locationType, locationType.getId())).withRel("update").withType("PUT"),
        linkTo(methodOn(LocationTypeController.class).delete(locationType.getId())).withRel("delete").withType("DELETE")
        );
  }
}
