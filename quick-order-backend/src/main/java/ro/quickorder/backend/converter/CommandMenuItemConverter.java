package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItemCommand;
import ro.quickorder.backend.model.dto.CommandMenuItemDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author R. Lupoaie
 */
@Component
public class CommandMenuItemConverter {

    @Autowired
    CommandConverter commandConverter;

    @Autowired
    MenuItemConverter menuItemConverter;

    public MenuItemCommand toCommandMenuItem(CommandMenuItemDto commandMenuItemDto) {
        if (commandMenuItemDto == null) {
            return null;
        }
        MenuItemCommand commandMenuItem =  new MenuItemCommand();

        commandMenuItem.setAmount(commandMenuItemDto.getAmount());
        //commandMenuItem.setCommand(commandConverter.toCommand(commandMenuItemDto.getCommandDto()));
        commandMenuItem.setMenuItem(menuItemConverter.toMenuItem(commandMenuItemDto.getMenuItemDto()));

        return commandMenuItem;
    }

    public CommandMenuItemDto toCommandMenuItemDto(MenuItemCommand commandMenuItem) {
        if (commandMenuItem == null) {
            return null;
        }
        CommandMenuItemDto commandMenuItemDto = new CommandMenuItemDto();

        commandMenuItemDto.setAmount(commandMenuItem.getAmount());
        //commandMenuItemDto.setCommandDto(commandConverter.toCommandDto(commandMenuItem.getCommand()));
        commandMenuItemDto.setMenuItemDto(menuItemConverter.toMenuItemDto(commandMenuItem.getMenuItem()));

        return commandMenuItemDto;
    }

    public List<MenuItemCommand> toCommandMenuItems(List<CommandMenuItemDto> commandMenuItemDtos) {
        List<MenuItemCommand> commandMenuItems = new ArrayList<>();
        if (commandMenuItemDtos == null) {
            return null;
        }
        for(int i=0;i< commandMenuItemDtos.size(); i++) {
            if (commandMenuItemDtos.get(i) == null) {
                return null;
            }
            MenuItemCommand commandMenuItem = new MenuItemCommand();

            commandMenuItem.setAmount(commandMenuItemDtos.get(i).getAmount());
            //commandMenuItem.setCommand(commandConverter.toCommand(commandMenuItemDtos.get(i).getCommandDto()));
            commandMenuItem.setMenuItem(menuItemConverter.toMenuItem(commandMenuItemDtos.get(i).getMenuItemDto()));
            commandMenuItems.add(commandMenuItem);
        }
        return commandMenuItems;
    }

    public List<CommandMenuItemDto> toCommandMenuItemDtos(List<MenuItemCommand> commandMenuItems) {
        List<CommandMenuItemDto> commandMenuItemDtos = new ArrayList<>();
        if (commandMenuItems == null) {
            return null;
        }
        for(int i=0;i< commandMenuItems.size(); i++) {
            if (commandMenuItems.get(i) == null) {
                return null;
            }
            CommandMenuItemDto commandMenuItemDto = new CommandMenuItemDto();

            commandMenuItemDto.setAmount(commandMenuItems.get(i).getAmount());
            //commandMenuItemDto.setCommandDto(commandConverter.toCommandDto(commandMenuItems.get(i).getCommand()));
            commandMenuItemDto.setMenuItemDto(menuItemConverter.toMenuItemDto(commandMenuItems.get(i).getMenuItem()));
            commandMenuItemDtos.add(commandMenuItemDto);
        }
        return commandMenuItemDtos;
    }

}
