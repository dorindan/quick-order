package ro.quickorder.backend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "feedback_menu_item")
public class Feedback {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;
    private int rating;
    private String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    public Feedback(int rating, String message, MenuItem menuItem) {
        this.rating = rating;
        this.message = message;
        this.menuItem = menuItem;
    }

    public Feedback() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return rating == feedback.rating &&
                Objects.equals(id, feedback.id) &&
                Objects.equals(message, feedback.message) &&
                Objects.equals(user, feedback.user) &&
                Objects.equals(menuItem, feedback.menuItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, message, user, menuItem);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id='" + id + '\'' +
                ", rating=" + rating +
                ", message='" + message + '\'' +
                ", user=" + user +
                ", menuItem=" + menuItem +
                '}';
    }
}
