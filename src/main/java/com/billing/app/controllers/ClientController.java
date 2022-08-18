package com.billing.app.controllers;

import com.billing.app.model.dtos.ClientDTO;
import com.billing.app.model.entities.Client;
import com.billing.app.model.mappers.ClientMapper;
import com.billing.app.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        clientService.saveClient(client);
        return new ResponseEntity<>(clientMapper.toDTO(client), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(clientMapper.toDTO(clientService.findById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(@RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "id", required = false) Long id,
                                                   @RequestParam(name = "email", required = false) String email) {
        if (name != null) {
            return new ResponseEntity<>(clientService.findAllByName(name)
                    .stream().map(clientMapper::toDTO).toList(), HttpStatus.OK);
        } else if (id != null) {
            return new ResponseEntity<>(List.of(clientMapper.toDTO(clientService.findById(id))), HttpStatus.OK);
        } else if (email != null) {
            return new ResponseEntity<>(List.of(clientMapper.toDTO(clientService.findByEmail(email))), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(clientService.findAll().stream()
                    .map(clientMapper::toDTO).toList(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.deleteClient(id),HttpStatus.ACCEPTED);
    }

}
