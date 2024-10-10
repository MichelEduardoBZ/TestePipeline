package br.com.michel.lixo.controller;

import br.com.michel.lixo.dto.vehicle.VehicleDTO;
import br.com.michel.lixo.dto.vehicle.VehicleUpdateDTO;
import br.com.michel.lixo.dto.vehicle.VehicleWithRouteDTO;
import br.com.michel.lixo.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<Page<VehicleDTO>> findAll(Pageable pageable) {
        Page<VehicleDTO> list = vehicleService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> insert(@RequestBody @Valid VehicleDTO vehicleDTO) {
        vehicleDTO = vehicleService.insert(vehicleDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vehicleDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(vehicleDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<VehicleDTO> update(@PathVariable Long id, @RequestBody VehicleUpdateDTO vehicleUpdateDTO) {
        VehicleDTO vehicleDTO = vehicleService.update(id, vehicleUpdateDTO);
        return ResponseEntity.ok().body(vehicleDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        vehicleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/insertVehicleWithRoute")
    public ResponseEntity<VehicleWithRouteDTO> insertVehicleWithRoute(@RequestBody @Valid VehicleWithRouteDTO vehicleWithRouteDTODTO) {
        vehicleWithRouteDTODTO = vehicleService.insertVehicleWithRoute(vehicleWithRouteDTODTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vehicleWithRouteDTODTO.getId()).toUri();
        return ResponseEntity.created(uri).body(vehicleWithRouteDTODTO);
    }
}
