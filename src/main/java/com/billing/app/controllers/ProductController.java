package com.billing.app.controllers;

import com.billing.app.model.dtos.ProductDTO;
import com.billing.app.model.mappers.ProductMapper;
import com.billing.app.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody @Valid ProductDTO productDTO) {
        return new ResponseEntity<>(productMapper.toDto(
                productService.saveProduct(productMapper.toEntity(productDTO))), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(productMapper.toDto(productService.findById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return new ResponseEntity<>(productService.findAll().stream()
                .map(productMapper::toDto).toList(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }
}
