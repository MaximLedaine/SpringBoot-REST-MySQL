package com.example.api.device;

import javax.validation.Valid;

import com.example.api.error.BadRequest;
import com.example.api.location.Location;
import com.example.api.location.LocationRepository;
import com.example.api.locationDevice.LocationDevice;
import com.example.api.locationDevice.LocationDeviceRepository;

import java.sql.Timestamp;
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

import net.minidev.json.JSONObject;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class DeviceController {
	@Autowired // This means to get the bean called DeviceRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
  private DeviceRepository deviceRepository;

  @Autowired // This means to get the bean called DeviceLocationRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
  private LocationDeviceRepository locationDeviceRepository;

  @Autowired // This means to get the bean called LocationRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
  private LocationRepository locationRepository;
  
  @Autowired
  private DeviceModelAssembler assembler;

  /**
   * Get devices.
   *
   * @param device the device
   * @return the device
   */
  // @GetMapping(path="/devices")
	// public @ResponseBody Iterable<Device> all() {
	// 	// This returns a JSON or XML with the devices
	// 	return repository.findAll();
  // }
  @GetMapping(path="/devices")
  CollectionModel<EntityModel<Device>> all(@RequestParam(value = "locationId", required = false) UUID locationId) throws EntityNotFoundException {
    List<EntityModel<Device>> devices;
    if(locationId == null) {
      devices = StreamSupport.stream(deviceRepository.findAll().spliterator(), false)
            .map(assembler::toModel) //
            .collect(Collectors.toList());
    } else {
      // something like this
      // look at the locationDeviceRepository and visit the link
      Location location = locationRepository.findById(locationId)
          .orElseThrow(() -> new EntityNotFoundException());
      List<LocationDevice> locationDevices = locationDeviceRepository.findByLocationAndRemovalDate(location, null);

      devices = StreamSupport.stream((locationDevices.stream().map(x -> x.getDevice())).spliterator(), false)
        .map(assembler::toModel) 
        .collect(Collectors.toList());
    }
      

    return CollectionModel.of(devices,
    linkTo(methodOn(DeviceController.class).all(null)).withSelfRel().withType("GET"),
    linkTo(methodOn(DeviceController.class).save(new Device())).withRel("create").withType("POST")
    );
  }
  
   /**
   * Create device.
   *
   * @param device the device
   * @return the device
   */
  @PostMapping("/devices")
  ResponseEntity<?> save(@RequestBody @Validated(Device.CreateValidation.class) Device newDevice) {
  
    Device device = deviceRepository.save(newDevice);
    EntityModel<Device> entityModel = assembler.toModel(device);
  
    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  /**
   * create or update device.
   *
   * @param device the device
   * @return the device
   */
  @PutMapping("/devices/{id}")
  ResponseEntity<?> update(@RequestBody @Validated(Device.CreateValidation.class)  Device newDevice, @PathVariable UUID id) {

    Device updatedDevice = deviceRepository.findById(id) //
      .map(device -> {
        device.setName(newDevice.getName());
        device.setDeviceOptions(newDevice.getDeviceOptions());
        return deviceRepository.save(device);
      }) //
      .orElseGet(() -> {
        return deviceRepository.save(newDevice);
      });

    EntityModel<Device> entityModel = assembler.toModel(updatedDevice);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  /**
   * Get device.
   *
   * @param device the device
   * @return the device
   */
  @GetMapping(value = "devices/{id}")
  public EntityModel<Device> one(@PathVariable(value = "id") UUID id) throws EntityNotFoundException {
      Device device = deviceRepository.findById(id)
                  .orElseThrow(() -> new EntityNotFoundException());
  
    return assembler.toModel(device);
  }

  /**
   * Delete device.
   *
   * @param device the device
   * @return the device
   */
  @DeleteMapping("/devices/{id}")
  ResponseEntity<?> delete(@PathVariable(value = "id") UUID id) throws EntityNotFoundException {
    Device device = deviceRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException());

    deviceRepository.delete(device);

    return ResponseEntity.noContent().build();
  }

  /**
   * Get device isntallation
   *
   * @return LocationDevice
   */
  @GetMapping("/devices/{id}/installation")
  LocationDevice getInstalledDevice(@PathVariable(value = "id") UUID id) throws EntityNotFoundException, BadRequest {
    Device device = deviceRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("device not found"));

    LocationDevice locationDevice = locationDeviceRepository.findByDeviceAndRemovalDate(device, null);
    if(locationDevice == null)
      throw new EntityNotFoundException("Device is not installed anywhere");

    return locationDevice;
  }

    /**
   * Get device availability
   *
   * @return LocationDevice
   */
  @GetMapping("/devices/{id}/available")
  JSONObject getDeviceAvailable(@PathVariable(value = "id") UUID id) throws EntityNotFoundException, BadRequest {
    JSONObject res = new JSONObject();
    res.appendField("available", true);

    Device device = deviceRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("device not found"));

    LocationDevice locationDevice = locationDeviceRepository.findByDeviceAndRemovalDate(device, null);
    if(locationDevice != null)
      res.appendField("available", false);
      
    return res;
  }

   /**
   * Install device on location.
   *
   * @param Location
   * @return no content
   */
  @PostMapping("/devices/{id}/installation")
  ResponseEntity<?> installDevice(@PathVariable(value = "id") UUID id, @RequestBody @Valid Location l) throws EntityNotFoundException, BadRequest {
    Device device = deviceRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("device not found"));

    LocationDevice ld = locationDeviceRepository.findByDeviceAndRemovalDate(device, null);
    if(ld != null) throw new BadRequest("device has already been installed");

    Location location = locationRepository.findById(l.getId())
              .orElseThrow(() -> new EntityNotFoundException("location not found"));
       
    // install device on location
    LocationDevice locationDevice = new LocationDevice(location, device);
    locationDeviceRepository.save(locationDevice);

  
    return ResponseEntity.noContent().build();
  }

  /**
   * remove device from location.
   *
   * @return no content
   */
  @DeleteMapping("/devices/{id}/installation")
  ResponseEntity<?> removeDevice(@PathVariable(value = "id") UUID id) throws EntityNotFoundException, BadRequest {
    // the errors that are used are not yet correct 

    // throw error if device does not exist
    Device device = deviceRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("device not found"));


    // try to get the locationDevice
    // if it doesn't exist throw an error
    // if it does => setActive to false
    // and save to database

    LocationDevice locationDevice = locationDeviceRepository.findByDeviceAndRemovalDate(device, null);
    // throw error when the device is not installed
    if(locationDevice == null) throw new BadRequest("device is not installed anywhere");

    // if the locationDevice exists
    Timestamp now = new Timestamp(System.currentTimeMillis());
    locationDevice.setRemovalDate(now);
    locationDeviceRepository.save(locationDevice);
  
    return ResponseEntity.noContent().build();
  }
}
