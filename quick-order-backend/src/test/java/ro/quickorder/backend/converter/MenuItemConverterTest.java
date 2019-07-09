package ro.quickorder.backend.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link MenuItemConverter}
 *
 * @author R. Lupoaie
 */
public class MenuItemConverterTest {

    private MenuItemConverter menuItemConverter = new MenuItemConverter(new IngredientConverter(),new MenuItemTypeConverter());

    @Test
    public void testConvertMenuItemToDto() {
        MenuItem menuItem = new MenuItem("name1", "description", 20, 45, new MenuItemType("desert"));
        MenuItemDto menuItemDto = menuItemConverter.toMenuItemDto(menuItem);
        assertEquals(menuItem.getName(), menuItemDto.getName());
        assertEquals(menuItem.getDescription(), menuItemDto.getDescription());
        assertEquals(menuItem.getPreparationDurationInMinutes(), menuItemDto.getPreparationDurationInMinutes());
        assertEquals(menuItem.getPrice(), menuItemDto.getPrice());
        assertEquals(menuItem.getMenuItemType().getType(), menuItemDto.getMenuItemTypeDto().getType());
    }

    @Test
    public void testConvertMenuItemToDtoWhenMenuItemIsNull() {
        MenuItemDto menuItemDto = menuItemConverter.toMenuItemDto(null);
        assertNull(menuItemDto);
    }

    @Test
    public void testConvertDtoToMenuItem() {
        MenuItemDto menuItemDto = new MenuItemDto("name1", "description", 20, 12, new MenuItemTypeDto("desert"));
        MenuItem menuItem = menuItemConverter.toMenuItem(menuItemDto);
        assertEquals(menuItemDto.getName(), menuItem.getName());
        assertEquals(menuItemDto.getDescription(), menuItem.getDescription());
        assertEquals(menuItemDto.getPreparationDurationInMinutes(), menuItem.getPreparationDurationInMinutes());
        assertEquals(menuItemDto.getPrice(), menuItem.getPrice());
        assertEquals(menuItemDto.getMenuItemTypeDto().getType(), menuItem.getMenuItemType().getType());
        assertNull(menuItem.getCommands());
        assertNull(menuItem.getFeedbacks());
        assertNull(menuItem.getIngredients());
    }

    @Test
    public void testConvertDtoToMenuItemWhenDtoIsNull() {
        MenuItem menuItem = menuItemConverter.toMenuItem(null);
        assertNull(menuItem);
    }
}