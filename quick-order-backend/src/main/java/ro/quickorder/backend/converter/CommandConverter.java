package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.dto.CommandDto;

@Component
public class CommandConverter {

    public Command convertCommandDtoToCommand(CommandDto commandDto){
        Command command= new Command();
        command.setCommandName(commandDto.getCommandName());
        command.setSpecification(commandDto.getSpecification());
        command.setPacked(commandDto.isPacked());
        command.setStatus(commandDto.getStatus());
        return command;
    }

    public CommandDto convertCommandToCommandDto(Command command){
        CommandDto commandDto= new CommandDto();
        commandDto.setCommandName(command.getCommandName());
        commandDto.setSpecification(command.getSpecification());
        commandDto.setPacked(command.isPacked());
        commandDto.setStatus(command.getStatus());
        return commandDto;
    }

}
