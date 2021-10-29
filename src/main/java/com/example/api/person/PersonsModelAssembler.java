package com.example.api.person;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class PersonsModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {

  @Override
  public EntityModel<Person> toModel(Person person) {

    return EntityModel.of(person, //
        linkTo(methodOn(PersonController.class).one(person.getId())).withSelfRel().withType("GET"),
        linkTo(methodOn(PersonController.class).all()).withRel("all").withType("GET"),
        linkTo(methodOn(PersonController.class).save(person)).withRel("create").withType("POST"),
        linkTo(methodOn(PersonController.class).update(person, person.getId())).withRel("update").withType("PUT"),
        linkTo(methodOn(PersonController.class).delete(person.getId())).withRel("delete").withType("DELETE")
        );
  }
}
