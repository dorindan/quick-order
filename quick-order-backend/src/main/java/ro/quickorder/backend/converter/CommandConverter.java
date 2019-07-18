package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.dto.CommandDto;

import java.util.ArrayList;
import java.util.List;

/**
 *  Converts Commands to their corresponding DTO and vice versa.
 * @author R. Lupoaie
 */
@Component
public class CommandConverter {

    @Autowired
    MenuItemCommandConverter menuItemCommandConverter;

    public CommandConverter(){

    }
    public CommandConverter(MenuItemCommandConverter menuItemCommandConverter){
        this.menuItemCommandConverter = menuItemCommandConverter;
    }

    public Command toCommand(CommandDto commandDto) {
        if (commandDto == null) {
            return null;
        }
        Command command = new Command();
        command.setCommandName(commandDto.getCommandName());
        command.setSpecification(commandDto.getSpecification());
        command.setPacked(commandDto.isPacked());
        command.setStatus(commandDto.getStatus());
        command.setMenuItemCommands(menuItemCommandConverter.toCommandMenuItems(commandDto.getMenuItemCommandDtos()));
        return command;
    }

    public CommandDto toCommandDto(Command command) {
        if (command == null) {
            return null;
        }
        CommandDto commandDto = new CommandDto();
        commandDto.setCommandName(command.getCommandName());
        commandDto.setSpecification(command.getSpecification());
        commandDto.setPacked(command.isPacked());
        commandDto.setStatus(command.getStatus());
        commandDto.setMenuItemCommandDtos(menuItemCommandConverter.toCommandMenuItemDtos(command.getMenuItemCommands()));
        return commandDto;
    }
}
