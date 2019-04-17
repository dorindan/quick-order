package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.MenuItemType;

import java.util.List;
import java.util.Set;

/**
 *  Data transfer object for {@link ro.quickorder.backend.model.MenuItem}
 *
 * @author R. Lupoaie
 */
public class MenuItemDto {
    private String name;
    private String description;
    private Integer preparationDurationInMinutes;
    private Integer price;
    private MenuItemTypeDto menuItemTypeDto;
    private Set<IngredientDto> ingredients;

    public MenuItemDto() {
    }

    public MenuItemDto(String name, String description, Integer preparationDurationInMinutes, Integer price) {
        this.name = name;
        this.description = description;
        this.preparationDurationInMinutes = preparationDurationInMinutes;
        this.price = price;
    }

    public MenuItemDto(String name, String description, Set<IngredientDto> ingredientDtos, Integer preparationDurationInMinutes, Integer price) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredientDtos;
        this.preparationDurationInMinutes = preparationDurationInMinutes;
        this.price = price;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public MenuItemTypeDto getMenuItemTypeDto() {
        return menuItemTypeDto;
    }

    public void setMenuItemTypeDto(MenuItemTypeDto menuItemTypeDto) {
        this.menuItemTypeDto = menuItemTypeDto;
    }

    public Set<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }



}
