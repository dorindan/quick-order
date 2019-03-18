package ro.quickorder.backend.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "feedback_menu_item")
public class Feedback {
    @Id
    private Long id;
    private int rating;
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MenuItem user;

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
}
