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
    CommandMenuItemConverter commandMenuItemConverter;

    public Command toCommand(CommandDto commandDto) {
        if (commandDto == null) {
            return null;
        }
        Command command = new Command();
        command.setCommandName(commandDto.getCommandName());
        command.setSpecification(commandDto.getSpecification());
        command.setPacked(commandDto.isPacked());
        command.setStatus(commandDto.getStatus());
        command.setCommandMenuItems(commandMenuItemConverter.toCommandMenuItems(commandDto.getCommandMenuItemDtos()));
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
        commandDto.setCommandMenuItemDtos(commandMenuItemConverter.toCommandMenuItemDtos(command.getCommandMenuItems()));
        return commandDto;
    }

    public List<Command> toCommands(List<CommandDto> commandDto) {
        List<Command> commands = new ArrayList<>();
        for(int i=0;i< commandDto.size(); i++) {
            if (commandDto.get(i) == null) {
                return null;
            }
            Command command = new Command();
            command.setCommandName(commandDto.get(i).getCommandName());
            command.setSpecification(commandDto.get(i).getSpecification());
            command.setPacked(commandDto.get(i).isPacked());
            command.setStatus(commandDto.get(i).getStatus());
            commands.add(command);
        }
        return commands;
    }

    public List<CommandDto> toCommandDtos(List<Command> command) {
        List<CommandDto> commandDtos = new ArrayList<>();
        for(int i=0;i< command.size(); i++) {
            if (command.get(i) == null) {
                return null;
            }
            CommandDto commandDto = new CommandDto();
            commandDto.setCommandName(command.get(i).getCommandName());
            commandDto.setSpecification(command.get(i).getSpecification());
            commandDto.setPacked(command.get(i).isPacked());
            commandDto.setStatus(command.get(i).getStatus());
            commandDtos.add(commandDto);
        }
        return commandDtos;
    }
}
