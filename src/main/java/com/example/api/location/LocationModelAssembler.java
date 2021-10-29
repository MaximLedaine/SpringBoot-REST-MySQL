package com.example.api.location;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class LocationModelAssembler implements RepresentationModelAssembler<Location, EntityModel<Location>> {

  @Override
  public EntityModel<Location> toModel(Location location) {

    return EntityModel.of(location, //
        linkTo(methodOn(LocationController.class).one(location.getId())).withSelfRel().withType("GET"),
        linkTo(methodOn(LocationController.class).all(null)).withRel("all").withType("GET"),
        linkTo(methodOn(LocationController.class).save(location)).withRel("create").withType("POST"),
        linkTo(methodOn(LocationController.class).update(location, location.getId())).withRel("update").withType("PUT"),
        linkTo(methodOn(LocationController.class).delete(location.getId())).withRel("delete").withType("DELETE")
        );
  }
}
