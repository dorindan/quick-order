package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;

import java.util.Arrays;
import java.util.List;

/**
 *Converts MenuItemType to their corresponding DTO and vice versa.
 *
 * @author R. Lupoaie
 */
@Component
public class MenuItemTypeConverter {

    public MenuItemType toMenuItemType(MenuItemTypeDto menuItemTypeDto) {
        if (menuItemTypeDto == null) {
            return null;
        }
        MenuItemType menuItemType = new MenuItemType();
        menuItemType.setType(menuItemTypeDto.getType());

        return menuItemType;
    }

    public MenuItemTypeDto toMenuItemTypeDto(MenuItemType menuItemType) {
        if (menuItemType == null) {
            return null;
        }
        MenuItemTypeDto menuItemTypeDto = new MenuItemTypeDto();
        menuItemTypeDto.setType(menuItemType.getType());
        return menuItemTypeDto;
    }
}
