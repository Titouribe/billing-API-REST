package com.billing.app.services;

import com.billing.app.model.entities.Product;

import java.util.List;

public interface IProductService {
    public Product saveProduct(Product product);
    public Product findById(Long id);
    public String deleteProduct(Long id);
    public List<Product> findAll();
}
