package ro.quickorder.backend.model;

import org.hibernate.validator.constraints.Currency;

import javax.persistence.*;

@Entity
public class Bill {
    @Id
    private Long id;
    private boolean voucher;
    private int sale;
    private Currency total;

    //waiter of type users
    @OneToMany
    @JoinColumn(name = "userId")
    private Users user;

    @OneToOne(mappedBy = "bill")
    private Command command;


}


