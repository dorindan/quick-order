package ro.quickorder.backend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Command {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;
    private String commandName;
    private String specification;
    private boolean isPacked;
    private String status;
    @ManyToMany
    @JoinTable(name = "menu_item_command",
            joinColumns = {@JoinColumn(name = "command_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_item_id")})
    private List<MenuItemCommand> commandMenuItems;
    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableFood table;
    @OneToMany(mappedBy = "command")
    private List<Reservation> reservations;
    @ManyToMany(mappedBy = "commands")
    private List<User> users;

    public Command(String commandName, String specification, boolean isPacked, String status, TableFood table) {
        this.commandName = commandName;
        this.specification = specification;
        this.isPacked = isPacked;
        this.status = status;
        this.table = table;
    }

    public Command() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MenuItemCommand> getMenuItems() {
        return commandMenuItems;
    }

    public void setMenuItems(List<MenuItemCommand> commandMenuItems) {
        this.commandMenuItems = commandMenuItems;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public TableFood getTable() {
        return table;
    }

    public void setTable(TableFood table) {
        this.table = table;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return isPacked == command.isPacked &&
                Objects.equals(id, command.id) &&
                Objects.equals(commandName, command.commandName) &&
                Objects.equals(specification, command.specification) &&
                Objects.equals(status, command.status) &&
                Objects.equals(commandMenuItems, command.commandMenuItems) &&
                Objects.equals(bill, command.bill) &&
                Objects.equals(table, command.table) &&
                Objects.equals(reservations, command.reservations) &&
                Objects.equals(users, command.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commandName, specification, isPacked, status, commandMenuItems, bill, table, reservations, users);
    }

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", commandName='" + commandName + '\'' +
                ", specification='" + specification + '\'' +
                ", isPacked=" + isPacked +
                ", status='" + status + '\'' +
                ", bill=" + bill.getId() +
                ", table=" + table +
                '}';
    }

    public List<MenuItemCommand> getCommandMenuItems() {
        return commandMenuItems;
    }

    public void setCommandMenuItems(List<MenuItemCommand> commandMenuItems) {
        this.commandMenuItems = commandMenuItems;
    }
}
