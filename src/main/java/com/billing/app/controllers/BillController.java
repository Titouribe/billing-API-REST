package com.billing.app.controllers;

import com.billing.app.model.dtos.BillDTO;
import com.billing.app.model.mappers.BillMapper;
import com.billing.app.services.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private IBillService billService;

    @PostMapping
    public ResponseEntity<BillDTO> saveBill(@RequestBody BillDTO billDTO) {
        return new ResponseEntity<>(billMapper.toDto(
                billService.saveBill(billMapper.toEntity(billDTO))), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(billMapper.toDto(billService.findById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BillDTO>> findAll() {
        return new ResponseEntity<>(billService.findAll().stream()
                .map(billMapper::toDto).toList(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Long id) {
        return new ResponseEntity<>(billService.deleteBill(id),HttpStatus.ACCEPTED);
    }
}
