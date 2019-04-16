package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.MenuItemDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link MenuItemConverter}
 *
 * @author R. Lupoaie
 */
public class MenuItemConverterTest {
    private MenuItemConverter menuItemConverter = new MenuItemConverter();

    @Test
    public void testConvertMenuItemToDto() {
        MenuItem menuItem = new MenuItem("name1", "description", 20, 45);
        MenuItemDto menuItemDto = menuItemConverter.toMenuItemDto(menuItem);
        assertEquals(menuItem.getName(), menuItemDto.getName());
        assertEquals(menuItem.getDescription(), menuItemDto.getDescription());
        assertEquals(menuItem.getPreparationDurationInMinutes(), menuItemDto.getPreparationDurationInMinutes());
    }

    @Test
    public void testConvertMenuItemToDtoWhenMenuItemIsNull() {
        MenuItemDto menuItemDto = menuItemConverter.toMenuItemDto(null);
        assertNull(menuItemDto);
    }

    @Test
    public void testConvertDtoToMenuItem() {
        MenuItemDto menuItemDto = new MenuItemDto("name1", "description", 20);
        MenuItem menuItem = menuItemConverter.toMenuItem(menuItemDto);
        assertEquals(menuItemDto.getName(), menuItem.getName());
        assertEquals(menuItemDto.getDescription(), menuItem.getDescription());
        assertEquals(menuItemDto.getPreparationDurationInMinutes(), menuItem.getPreparationDurationInMinutes());
        assertNull(menuItem.getCommands());
        assertNull(menuItem.getFeedbacks());
        assertNull(menuItem.getIngredients());
        assertNull(menuItem.getMenuItemType());
    }

    @Test
    public void testConvertDtoToMenuItemWhenDtoIsNull() {
        MenuItem menuItem = menuItemConverter.toMenuItem(null);
        assertNull(menuItem);
    }
}