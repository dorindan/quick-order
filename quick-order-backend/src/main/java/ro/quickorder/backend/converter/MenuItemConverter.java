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

    public MenuItem toMenuItem(MenuItemDto menuItemDtoDto) {
        if (menuItemDtoDto == null) {
            return null;
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDtoDto.getName());
        menuItem.setDescription(menuItemDtoDto.getDescription());
        menuItem.setPreparationDurationInMinutes(menuItemDtoDto.getPreparationDurationInMinutes());
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
        return menuItemDto;
    }

}