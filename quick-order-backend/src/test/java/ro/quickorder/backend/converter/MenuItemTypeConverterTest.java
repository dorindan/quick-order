package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link MenuItemTypeConverter}
 *
 * @author R. Lupoaie
 */
public class MenuItemTypeConverterTest {

    private MenuItemTypeConverter menuItemTypeConverter = new MenuItemTypeConverter();

    @Test
    public void testToMenuItemType() {
        MenuItemType reservation = new MenuItemType("vegetable");

        MenuItemTypeDto reservationDto = menuItemTypeConverter.toMenuItemTypeDto(reservation);

        assertEquals(reservation.getType(), reservationDto.getType());
    }

    @Test
    public void testConvertMenuItemTypeToDtoWhenMenuItemTypeIsNull() {
        MenuItemTypeDto reservationDto = menuItemTypeConverter.toMenuItemTypeDto(null);

        assertNull(reservationDto);
    }


    @Test
    public void testToMenuItemTypeDto() {
        MenuItemTypeDto reservationDto = new MenuItemTypeDto("condimente");

        MenuItemType reservation = menuItemTypeConverter.toMenuItemType(reservationDto);

        assertEquals(reservationDto.getType(), reservation.getType());
        assertNull(reservation.getMenuItems());
    }

    @Test
    public void testConvertDtoToMenuItemTypeWhenDtoIsNull() {
        MenuItemType reservation = menuItemTypeConverter.toMenuItemType(null);

        assertNull(reservation);
    }
}