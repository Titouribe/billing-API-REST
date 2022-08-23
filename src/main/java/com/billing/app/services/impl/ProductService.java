package com.billing.app.services.impl;

import com.billing.app.constants.Constants;
import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.model.entities.Product;
import com.billing.app.repositories.IProductRepository;
import com.billing.app.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }

    @Override
    public String deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
            return validConstants.foundAndDelete(productOptional.get().getName());
        } else {
            return errorsConstants.notFound(Constants.PRODUCT,String.valueOf(id));
        }
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
