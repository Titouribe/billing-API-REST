package com.billing.app.model.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50, message = "Product name must be between 1 and 50 characters")
    private String name;

    @Min(value = 1, message = "Price must be greater than 0")
    private BigDecimal price;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;
}
