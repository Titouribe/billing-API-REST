package com.billing.app.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long id;

    private String description;

    private String observation;


    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @ManyToOne
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
    private Set<BillLine> billLines = new HashSet<>();

    public void addBillLine(BillLine billLine){
        if (billLine != null){
            if (this.billLines == null){
                this.billLines = new HashSet<>();
            }
            this.billLines.add(billLine);
        }
    }

    public BigDecimal calculateTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (BillLine billLine : this.billLines){
            total = total.add(billLine.calculateAmount());
        }
        return total;
    }
}
