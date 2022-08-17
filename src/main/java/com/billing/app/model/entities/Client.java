package com.billing.app.model.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "date_created")
    @CreationTimestamp
    @NotNull
    private Date dateCreated;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Bill> bills = new HashSet<>();

    public void addBills(Bill bill){
        if (bill != null){
            if (this.bills == null){
                this.bills = new HashSet<>();
            }
            this.bills.add(bill);
            bill.setClient(this);
        }
    }
}
