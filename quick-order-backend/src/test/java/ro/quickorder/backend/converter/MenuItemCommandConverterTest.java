package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemCommand;
import ro.quickorder.backend.model.dto.MenuItemCommandDto;
import ro.quickorder.backend.model.dto.MenuItemDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link MenuItemCommandConverter}
 *
 * @author R. Lupoaie
 */
public class MenuItemCommandConverterTest {

    MenuItemCommandConverter menuItemCommandConverter = new MenuItemCommandConverter(
            new MenuItemConverter(new IngredientConverter(), new MenuItemTypeConverter()));


    @Test
    public void testToMenuItemCommands() {
        List<MenuItemCommandDto> menuItemCommandDtos = new ArrayList<>();
        MenuItemCommandDto menuItemCommandDto = new MenuItemCommandDto();
        menuItemCommandDto.setAmount(2);
        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemCommandDto.setMenuItemDto(menuItemDto);
        menuItemCommandDtos.add(menuItemCommandDto);
        menuItemCommandDtos.add(new MenuItemCommandDto());
        menuItemCommandDtos.add(new MenuItemCommandDto());

        List<MenuItemCommand> menuItemCommands = menuItemCommandConverter.toMenuItemCommands(menuItemCommandDtos);
        assertEquals(3, menuItemCommands.size());
        assertEquals(new Integer(2), menuItemCommands.get(0).getAmount());
    }

    @Test
    public void testToMenuItemCommandDtos() {
        List<MenuItemCommand> menuItemCommands = new ArrayList<>();
        MenuItemCommand menuItemCommand = new MenuItemCommand();
        menuItemCommand.setAmount(2);
        MenuItem menuItem = new MenuItem();
        menuItemCommand.setMenuItem(menuItem);
        menuItemCommands.add(menuItemCommand);
        menuItemCommands.add(new MenuItemCommand());
        menuItemCommands.add(new MenuItemCommand());

        List<MenuItemCommandDto> menuItemCommandDtos = menuItemCommandConverter.toMenuItemCommandDtos(menuItemCommands);
        assertEquals(3, menuItemCommandDtos.size());
        assertEquals(new Integer(2), menuItemCommandDtos.get(0).getAmount());
    }


}
