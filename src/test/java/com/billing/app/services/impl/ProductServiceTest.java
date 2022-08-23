package com.billing.app.services.impl;

import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.model.entities.Product;
import com.billing.app.repositories.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ValidConstants validConstants;

    @Mock
    private ErrorsConstants errorsConstants;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("test");
        product.setDateCreated(new Date());
        product.setPrice(BigDecimal.valueOf(10));
    }

    @Test
    void deleteClientWhenClientExist() {
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        assertEquals(productService.deleteProduct(any()), validConstants.foundAndUpdated(product.getName()));
    }

    @Test
    void deleteClientWhenClientDontExist() {
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertEquals(productService.deleteProduct(any()), errorsConstants.notFound("Product"));
    }

}
