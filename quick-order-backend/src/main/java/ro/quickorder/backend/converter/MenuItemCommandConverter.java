package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItemCommand;
import ro.quickorder.backend.model.dto.MenuItemCommandDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    public MenuItemCommandDto toMenuItemCommandDto(MenuItemCommand menuItemCommand) {
        if (menuItemCommand == null) {
            return null;
        }
        MenuItemCommandDto menuItemCommandDto = new MenuItemCommandDto();
        menuItemCommandDto.setAmount(menuItemCommand.getAmount());
        menuItemCommandDto.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItemCommand.getMenuItem()));
        return menuItemCommandDto;

    }

    public MenuItemCommand toMenuItemCommand(MenuItemCommandDto menuItemCommandDto) {

            if (menuItemCommandDto == null) {
                return null;
            }
            MenuItemCommand menuItemCommand = new MenuItemCommand();

            menuItemCommand.setAmount(menuItemCommandDto.getAmount());
            menuItemCommand.setMenuItem(menuItemConverter.toMenuItem(menuItemCommandDto.getMenuItemDto()));
            return menuItemCommand;
    }

    public List<MenuItemCommand> toMenuItemCommands(List<MenuItemCommandDto> menuItemCommandDtos) {
        if (menuItemCommandDtos == null) {
            return null;
        }
        return menuItemCommandDtos.stream().map(this::toMenuItemCommand).collect(Collectors.toList());
    }

    public List<MenuItemCommandDto> toMenuItemCommandDtos(List<MenuItemCommand> menuItemCommands) {
        if (menuItemCommands == null) {
            return null;
        }
        return menuItemCommands.stream().map(this::toMenuItemCommandDto).collect(Collectors.toList());
    }
}
