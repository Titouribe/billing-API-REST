package com.billing.app.services.impl;

import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.exceptions.RequestException;
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
    void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        assertEquals(product, productService.saveProduct(product));
    }
    @Test
    void testFindByIdWhenProductExist() {
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        assertEquals(product.getName(), productService.findById(any()).getName());
    }

    @Test
    void testFindByIdWhenProductDontExist() {
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            productService.findById(1L);
        });
    }

    @Test
    void testDeleteProductWhenProductExist() {
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        assertEquals(productService.deleteProduct(any()), validConstants.foundAndUpdated(product.getName()));
    }

    @Test
    void testDeleteProductWhenProductDontExist() {
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            productService.deleteProduct(1L);
        });
    }
}
