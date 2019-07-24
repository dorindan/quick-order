package ro.quickorder.backend.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ro.quickorder.backend.model.enumeration.CommandStatus;

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
    @Enumerated(EnumType.STRING)
    private CommandStatus status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User users;
    @OneToMany(mappedBy = "command")
    private List<MenuItemCommand> menuItemCommands;
    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "table_id")
    private TableFood table;
    @OneToMany(mappedBy = "command")
    private List<Reservation> reservations;

    public Command(String commandName, String specification, boolean isPacked, CommandStatus status, TableFood table) {
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

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public List<MenuItemCommand> getMenuItems() {
        return menuItemCommands;
    }

    public void setMenuItems(List<MenuItemCommand> menuItemCommands) {
        this.menuItemCommands = menuItemCommands;
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

    public User getUser() {
        return users;
    }

    public void setUser(User users) {
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
                Objects.equals(menuItemCommands, command.menuItemCommands) &&
                Objects.equals(bill, command.bill) &&
                Objects.equals(table, command.table) &&
                Objects.equals(reservations, command.reservations) &&
                Objects.equals(users, command.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commandName, specification, isPacked, status, menuItemCommands, bill, table, reservations, users);
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

    public List<MenuItemCommand> getMenuItemCommands() {
        return menuItemCommands;
    }

    public void setMenuItemCommands(List<MenuItemCommand> menuItemCommands) {
        this.menuItemCommands = menuItemCommands;
    }
}
