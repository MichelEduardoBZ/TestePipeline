package br.com.michel.lixo.controller;

import br.com.michel.lixo.dto.route.RouteContainerDTO;
import br.com.michel.lixo.dto.route.RouteDTO;
import br.com.michel.lixo.dto.route.RouteUpdateDTO;
import br.com.michel.lixo.dto.route.RouteVehicleDTO;
import br.com.michel.lixo.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public ResponseEntity<Page<RouteDTO>> findAll(Pageable pageable) {
        Page<RouteDTO> list = routeService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RouteDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RouteDTO> insert(@RequestBody @Valid RouteDTO routeDTO) {
        routeDTO = routeService.insert(routeDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(routeDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(routeDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RouteDTO> update(@PathVariable Long id, @RequestBody RouteUpdateDTO routeUpdateDTO) {
        RouteDTO routeDTO = routeService.update(id, routeUpdateDTO);
        return ResponseEntity.ok().body(routeDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/insertRouteWithVehicle")
    public ResponseEntity<RouteVehicleDTO> insertRouteWithVehicle(@RequestBody @Valid RouteVehicleDTO routeDTO) {
        routeDTO = routeService.insertRouteWithVehicle(routeDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(routeDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(routeDTO);
    }

    @PostMapping(value = "/addRouteToContainer")
    public ResponseEntity<RouteContainerDTO> addRouteToContainer(@RequestBody @Valid RouteContainerDTO routeContainerDTO) {
        routeContainerDTO = routeService.addRouteToContainer(routeContainerDTO);
        return ResponseEntity.ok().body(routeContainerDTO);
    }
}
