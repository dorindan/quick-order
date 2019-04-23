package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;

/**
 * Converts MenuItemType to their corresponding DTO and vice versa.
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
