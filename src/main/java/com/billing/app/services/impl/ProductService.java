package com.billing.app.services.impl;

import com.billing.app.constants.Constants;
import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.exceptions.RequestException;
import com.billing.app.model.entities.Product;
import com.billing.app.repositories.IProductRepository;
import com.billing.app.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ValidConstants validConstants;

    @Autowired
    private ErrorsConstants errorsConstants;

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RequestException("401", errorsConstants
                        .notFound(Constants.PRODUCT, String.valueOf(id))));
    }

    @Override
    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RequestException("401", errorsConstants
                        .notFound(Constants.PRODUCT, String.valueOf(id))));
        productRepository.delete(product);
        return validConstants.foundAndDelete(product.getName());
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
