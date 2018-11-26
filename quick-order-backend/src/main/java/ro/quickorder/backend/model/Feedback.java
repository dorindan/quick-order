package ro.quickorder.backend.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Feedback {
    @Id
    private Long id;
    private int rating;
    private String message;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;

    public Feedback(Long id, int rating, String message, MenuItem menuItem) {
        this.id = id;
        this.rating = rating;
        this.message = message;
        this.menuItem = menuItem;
    }

    public Feedback() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
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
                Objects.equals(menuItem, feedback.menuItem) &&
                Objects.equals(user, feedback.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, message, menuItem, user);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", rating=" + rating +
                ", message='" + message + '\'' +
                ", menuItem=" + menuItem +
                ", user=" + user +
                '}';
    }
}
