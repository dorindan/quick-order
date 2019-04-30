package ro.quickorder.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Bill {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;
    private boolean voucher;
    private int salePercentage;
    private BigDecimal total;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @OneToOne(mappedBy = "bill")
    @JsonIgnore
    private Command command;

    public Bill(boolean voucher, int salePercentage, BigDecimal total, User user, Command command) {
        this.voucher = voucher;
        this.salePercentage = salePercentage;
        this.total = total;
        this.user = user;
        this.command = command;
    }

    public Bill() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean hasVoucher() {
        return voucher;
    }

    public void setVoucher(boolean voucher) {
        this.voucher = voucher;
    }

    public int getSalePercentage() {
        return salePercentage;
    }

    public void setSalePercentage(int salePercentage) {
        this.salePercentage = salePercentage;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return voucher == bill.voucher &&
                salePercentage == bill.salePercentage &&
                Objects.equals(id, bill.id) &&
                Objects.equals(total, bill.total) &&
                Objects.equals(user, bill.user) &&
                Objects.equals(command, bill.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voucher, salePercentage, total, user, command);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", voucher=" + voucher +
                ", salePercentage=" + salePercentage +
                ", total=" + total +
                ", command=" + command.getId() +
                '}';
    }
}


