package com.billing.app.controllers;

import com.billing.app.model.dtos.ClientDTO;
import com.billing.app.model.mappers.ClientMapper;
import com.billing.app.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody @Valid ClientDTO clientDTO) {
        return new ResponseEntity<>(clientMapper.toDTO(
                clientService.saveClient(clientMapper.toEntity(clientDTO))), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(clientMapper.toDTO(clientService.findById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(@RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "email", required = false) String email) {
        if (name != null) {
            return new ResponseEntity<>(clientService.findAllByName(name)
                    .stream().map(clientMapper::toDTO).toList(), HttpStatus.OK);
        } else if (email != null) {
            return new ResponseEntity<>(clientService.findAllByEmail(email)
                    .stream().map(clientMapper::toDTO).toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(clientService.findAll().stream()
                    .map(clientMapper::toDTO).toList(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.deleteClient(id), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.updateClient(id, clientMapper.toEntity(clientDTO)), HttpStatus.ACCEPTED);
    }

}
