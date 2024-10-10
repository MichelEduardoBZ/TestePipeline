package br.com.michel.lixo.controller;

import br.com.michel.lixo.dto.container.ContainerDTO;
import br.com.michel.lixo.dto.container.ContainerUpdateDTO;
import br.com.michel.lixo.service.ContainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/container")
public class ContainerController {

    @Autowired
    private ContainerService containerService;

    @GetMapping
    public ResponseEntity<Page<ContainerDTO>> findAll(Pageable pageable) {
        Page<ContainerDTO> list = containerService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContainerDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(containerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContainerDTO> insert(@RequestBody @Valid ContainerDTO containerDTO) {
        containerDTO = containerService.insert(containerDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(containerDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(containerDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ContainerDTO> update(@PathVariable Long id, @RequestBody @Valid ContainerUpdateDTO containerUpdateDTO) {
        ContainerDTO containerDTO = containerService.update(id, containerUpdateDTO);
        return ResponseEntity.ok().body(containerDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        containerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
