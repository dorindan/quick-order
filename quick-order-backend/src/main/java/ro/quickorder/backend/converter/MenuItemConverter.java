package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.MenuItemDto;

/**
 *Converts Commands to their corresponding DTO and vice versa.
 *
 *@author R. Lupoaie
 */
@Component
public class MenuItemConverter {

    public MenuItem toMenuItem(MenuItemDto menuItemDto) {
        if (menuItemDto == null) {
            return null;
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setIngredients(menuItemDto.getIngredients());
        menuItem.setMenuItemType(menuItemDto.getMenuItemType());
        return menuItem;
    }

    public MenuItemDto toMenuItemDto(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }
        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setName(menuItem.getName());
        menuItemDto.setDescription(menuItem.getDescription());
        menuItemDto.setPreparationDurationInMinutes(menuItem.getPreparationDurationInMinutes());
        menuItemDto.setPrice(menuItem.getPrice());
        menuItemDto.setIngredients(menuItem.getIngredients());
        menuItemDto.setMenuItemType(menuItem.getMenuItemType());
        return menuItemDto;
    }

}
