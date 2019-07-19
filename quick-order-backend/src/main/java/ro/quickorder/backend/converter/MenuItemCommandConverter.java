package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItemCommand;
import ro.quickorder.backend.model.dto.MenuItemCommandDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author R. Lupoaie
 */
@Component
public class MenuItemCommandConverter {

    @Autowired
    MenuItemConverter menuItemConverter;

    public MenuItemCommandConverter() {

    }

    public MenuItemCommandConverter(MenuItemConverter menuItemConverter) {
        this.menuItemConverter = menuItemConverter;
    }

    public List<MenuItemCommand> toMenuItemCommands(List<MenuItemCommandDto> menuItemCommandDtos) {
        List<MenuItemCommand> menuItemCommands = new ArrayList<>();
        if (menuItemCommandDtos == null) {
            return null;
        }
        for (int i = 0; i < menuItemCommandDtos.size(); i++) {
            if (menuItemCommandDtos.get(i) == null) {
                return null;
            }
            MenuItemCommand menuItemCommand = new MenuItemCommand();

            menuItemCommand.setAmount(menuItemCommandDtos.get(i).getAmount());
            menuItemCommand.setMenuItem(menuItemConverter.toMenuItem(menuItemCommandDtos.get(i).getMenuItemDto()));
            menuItemCommands.add(menuItemCommand);
        }
        return menuItemCommands;
    }

    public List<MenuItemCommandDto> toMenuItemCommandDtos(List<MenuItemCommand> menuItemCommands) {
        List<MenuItemCommandDto> menuItemCommandDtos = new ArrayList<>();
        if (menuItemCommands == null) {
            return null;
        }
        for (int i = 0; i < menuItemCommands.size(); i++) {
            if (menuItemCommands.get(i) == null) {
                return null;
            }
            MenuItemCommandDto menuItemCommandDto = new MenuItemCommandDto();

            menuItemCommandDto.setAmount(menuItemCommands.get(i).getAmount());
            menuItemCommandDto.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItemCommands.get(i).getMenuItem()));
            menuItemCommandDtos.add(menuItemCommandDto);
        }
        return menuItemCommandDtos;
    }
}
