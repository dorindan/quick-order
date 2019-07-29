package ro.quickorder.backend.model.dto;

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
    private Object img ;

    public MenuItemDto() {
    }

    public MenuItemDto(String name, String description, Integer preparationDurationInMinutes, Integer price, MenuItemTypeDto menuItemTypeDto) {
        this.name = name;
        this.description = description;
        this.preparationDurationInMinutes = preparationDurationInMinutes;
        this.price = price;
        this.menuItemTypeDto = menuItemTypeDto;
    }

    public MenuItemDto(String name, String description, MenuItemTypeDto menuItemType, Set<IngredientDto> ingredientDtos, Integer preparationDurationInMinutes, Integer price) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredientDtos;
        this.preparationDurationInMinutes = preparationDurationInMinutes;
        this.price = price;
        this.menuItemTypeDto = menuItemType;
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

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }
}
