package com.billing.app.model.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "bill_lines")
public class BillLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_line_id")
    private Long id;
    @NotNull
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 10, message = "Quantity must be less than 10")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public BigDecimal calculateAmount(){
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

}
