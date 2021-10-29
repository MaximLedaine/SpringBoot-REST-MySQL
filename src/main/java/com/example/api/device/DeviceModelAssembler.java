package com.example.api.device;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class DeviceModelAssembler implements RepresentationModelAssembler<Device, EntityModel<Device>> {

  @Override
  public EntityModel<Device> toModel(Device device) {

    return EntityModel.of(device, //
        linkTo(methodOn(DeviceController.class).one(device.getId())).withSelfRel().withType("GET"),
        linkTo(methodOn(DeviceController.class).all(null)).withRel("all").withType("GET"),
        linkTo(methodOn(DeviceController.class).save(device)).withRel("create").withType("POST"),
        linkTo(methodOn(DeviceController.class).update(device, device.getId())).withRel("update").withType("PUT"),
        linkTo(methodOn(DeviceController.class).delete(device.getId())).withRel("delete").withType("DELETE")
        );
  }
}
