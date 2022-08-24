package com.billing.app.controllers;

import com.billing.app.model.dtos.Pucharse;
import com.billing.app.model.dtos.PucharseResponse;
import com.billing.app.services.IPucharseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private IPucharseService pucharseService;

    @PostMapping
    public ResponseEntity<PucharseResponse> doOrder(@RequestBody @Valid Pucharse pucharse) {
        return new ResponseEntity<>(pucharseService.placeOrder(pucharse), HttpStatus.CREATED);
    }
}
