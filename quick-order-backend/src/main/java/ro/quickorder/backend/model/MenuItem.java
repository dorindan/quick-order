package ro.quickorder.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class MenuItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;
    private String name;
    private String description;
    private Integer preparationDurationInMinutes;
    private Integer price;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "menu_item_type_id")
    private MenuItemType menuItemType;
    @OneToMany(mappedBy = "menuItem")
    private List<Feedback> feedbacks;
    @ManyToMany
    @JoinTable(name = "menu_item_ingredient",
            joinColumns = {@JoinColumn(name = "menu_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "ingredient_id")})
    private Set<Ingredient> ingredients;

    public MenuItem(String name, String description, Integer preparationDurationInMinutes, Integer price,  MenuItemType menuItemType) {
        this.name = name;
        this.description = description;
        this.preparationDurationInMinutes = preparationDurationInMinutes;
        this.price = price;
        this.menuItemType = menuItemType;
    }

    public MenuItem(String name, String description, MenuItemType menuItemType, Integer preparationDurationInMinutes, Integer price, Set<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.preparationDurationInMinutes = preparationDurationInMinutes;
        this.price = price;
        this.ingredients = ingredients;
        this.menuItemType = menuItemType;
    }

    public MenuItem() {
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPreparationDurationInMinutes() {
        return preparationDurationInMinutes;
    }

    public void setPreparationDurationInMinutes(Integer preparationDurationInMinutes) {
        this.preparationDurationInMinutes = preparationDurationInMinutes;
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public void setMenuItemType(MenuItemType menuItemType) {
        this.menuItemType = menuItemType;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(id, menuItem.id) &&
                Objects.equals(name, menuItem.name) &&
                Objects.equals(description, menuItem.description) &&
                Objects.equals(preparationDurationInMinutes, menuItem.preparationDurationInMinutes) &&
                Objects.equals(menuItemType, menuItem.menuItemType) &&
                Objects.equals(feedbacks, menuItem.feedbacks) &&
                Objects.equals(ingredients, menuItem.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, preparationDurationInMinutes, menuItemType, feedbacks, ingredients);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", preparationDurationInMinutes=" + preparationDurationInMinutes +
                ", menuItemType=" + menuItemType +
                '}';
    }
}
