package br.com.michel.lixo.controller;

import br.com.michel.lixo.dto.user.UserContainerDTO;
import br.com.michel.lixo.dto.user.UserDTO;
import br.com.michel.lixo.dto.user.UserUpdateDTO;
import br.com.michel.lixo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> list = userService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO userDTO = userService.update(id, userUpdateDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping(value = "/adminPermission/{id}")
    public ResponseEntity<UserDTO> updatePermissionToAdmin(@PathVariable Long id) {
        UserDTO userDTO = userService.updatePermissionToAdmin(id);
        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/addUserToContainer")
    public ResponseEntity<UserContainerDTO> addUserToContainer(@RequestBody @Valid UserContainerDTO userContainerDTO) {
        userContainerDTO = userService.addUserToContainer(userContainerDTO);
        return ResponseEntity.ok().body(userContainerDTO);
    }
}
