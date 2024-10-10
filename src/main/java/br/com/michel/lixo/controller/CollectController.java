package br.com.michel.lixo.controller;

import br.com.michel.lixo.dto.collect.CollectDTO;
import br.com.michel.lixo.dto.collect.CollectUpdateDTO;
import br.com.michel.lixo.service.CollectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @GetMapping
    public ResponseEntity<Page<CollectDTO>> findAll(Pageable pageable) {
        Page<CollectDTO> list = collectService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CollectDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(collectService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CollectDTO> insert(@RequestBody @Valid CollectDTO collectDTO) {
        collectDTO = collectService.insert(collectDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(collectDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(collectDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CollectDTO> update(@PathVariable Long id, @RequestBody CollectUpdateDTO collectUpdateDTO) {
        CollectDTO collectDTO = collectService.update(id, collectUpdateDTO);
        return ResponseEntity.ok().body(collectDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        collectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
