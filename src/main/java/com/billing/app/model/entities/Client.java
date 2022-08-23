package com.billing.app.model.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "Name must not be empty")
    @Size(min = 1, max = 20, message = "Name must contain a maximum of 20 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name must not be empty")
    @Size(min = 1, max = 20, message = "last Name must contain a maximum of 20 characters")
    private String lastName;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Invalid Email, must contain an @")
    private String email;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client")
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
