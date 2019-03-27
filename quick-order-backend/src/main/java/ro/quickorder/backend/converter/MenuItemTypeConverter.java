package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;

/**
 *  Converts MenuItemTypes to their corresponding DTO and vice versa.
 * @author R. Lupoaie
 */
@Component
public class MenuItemTypeConverter {

    public MenuItemType toMenuItemType(MenuItemTypeDto menuItemTypeDto) {
        if (menuItemTypeDto == null) {
            return null;
        }
        MenuItemType reservation = new MenuItemType();
        reservation.setType(menuItemTypeDto.getType());
        return reservation;
    }

    public MenuItemTypeDto toMenuItemTypeDto(MenuItemType menuItemType) {
        if (menuItemType == null) {
            return null;
        }
        MenuItemTypeDto reservation = new MenuItemTypeDto();
        reservation.setType(menuItemType.getType());
        return reservation;
    }

}
