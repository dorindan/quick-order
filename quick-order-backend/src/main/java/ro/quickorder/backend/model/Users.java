package ro.quickorder.backend.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
public class Users {

    @Id
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    @ManyToMany
    @JoinTable(name = "user_command",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "command_id") })
    private List<Command> commands;

    public Users(Long id) {
        this.id = id;
    }

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) &&
                Objects.equals(feedbacks, users.feedbacks) &&
                Objects.equals(reservations, users.reservations) &&
                Objects.equals(commands, users.commands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, feedbacks, reservations, commands);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", feedbacks=" + feedbacks +
                ", reservations=" + reservations +
                ", commands=" + commands +
                '}';
    }
}
