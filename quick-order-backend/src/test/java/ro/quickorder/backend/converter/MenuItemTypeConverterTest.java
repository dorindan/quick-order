package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;

import static org.junit.Assert.*;

/**
 * @author R. Lupoaie
 */
public class MenuItemTypeConverterTest {

    private MenuItemTypeConverter menuItemTypeConverter = new MenuItemTypeConverter();

    @Test
    public void testConvertMenuItemTypeToMenuItemTypeDto() {
        MenuItemType menuItemType = new MenuItemType("condiment");

        MenuItemTypeDto actual = menuItemTypeConverter.toMenuItemTypeDto(menuItemType);

        assertEquals(menuItemType.getType(), actual.getType());
    }

    @Test
    public void testConvertMenuItemTypeDtoToMenuItemType() {
        MenuItemTypeDto menuItemTypeDto = new MenuItemTypeDto("condiment");

        MenuItemType actual = menuItemTypeConverter.toMenuItemType(menuItemTypeDto);

        assertEquals(menuItemTypeDto.getType(), actual.getType());
    }
}