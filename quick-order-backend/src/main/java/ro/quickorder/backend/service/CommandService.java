package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.CommandConverter;
import ro.quickorder.backend.converter.CommandMenuItemConverter;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.CommandMenuItem;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.CommandRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author R. Lupoaie
 */
@Service
public class CommandService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);

    @Autowired
    MenuItemService menuItemService;

    @Autowired
    MenuItemConverter menuItemConverter;

    @Autowired
    CommandRepository commandRepository;

    @Autowired
    CommandConverter commandConverter;

    @Autowired
    CommandMenuItemConverter commandMenuItemConverter;

    public CommandDto userHasOpenCommand(String userName) {
        Command command = commandRepository.findByStatus("Active");
        return commandConverter.toCommandDto(command);
    }

    public void updateCommand( CommandDto commandDto){
        System.out.println("Acuma incerc");
        Command command = commandRepository.findByCommandName(commandDto.getCommandName());
        if (command == null) {
            LOG.error("Command not found");
            throw new NotFoundException("Command not found");
        }
        command.setStatus(commandDto.getStatus());
        command.setPacked(commandDto.isPacked());
        command.setSpecification(commandDto.getSpecification());
        command.setCommandMenuItems(new ArrayList<>());
        for(int i=0;i<commandDto.getCommandMenuItemDtos().size();i++){
            CommandMenuItem commandMenuItem = commandMenuItemConverter.toCommandMenuItem(commandDto.getCommandMenuItemDtos().get(i));
            commandMenuItem.setCommand(command);
            command.getCommandMenuItems().add(commandMenuItem);
        }

        commandRepository.save(command);
    }



}
