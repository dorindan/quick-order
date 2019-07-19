package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.CommandConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemCommand;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.enumeration.CommandStatus;
import ro.quickorder.backend.repository.MenuItemCommandRepository;
import ro.quickorder.backend.repository.CommandRepository;
import ro.quickorder.backend.repository.MenuItemRepository;
import ro.quickorder.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author R. Lupoaie
 */
@Service
public class CommandService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);

    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private CommandConverter commandConverter;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuItemCommandRepository menuItemCommandRepository;
    @Autowired
    private UserRepository userRepository;

    public CommandDto userActiveCommand(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            LOG.error("User not found");
            throw new NotFoundException("User not found");
        }
        Command command = commandRepository.findActiveByUser(user, CommandStatus.ACTIVE);
        return commandConverter.toCommandDto(command);
    }

    public void updateCommand( CommandDto commandDto){
        Command command = commandRepository.findByCommandNameWithItems(commandDto.getCommandName());
        if (command == null) {
            LOG.error("Command not found");
            throw new NotFoundException("Command not found");
        }
        command.setStatus(commandDto.getStatus());
        command.setPacked(commandDto.isPacked());
        command.setSpecification(commandDto.getSpecification());
        command.setMenuItemCommands(combineMenuItemCommands(command, commandDto));

        commandRepository.save(command);
    }

    private List<MenuItemCommand> combineMenuItemCommands(Command command, CommandDto commandDto) {
        if(command.getMenuItemCommands() == null){
            command.setMenuItemCommands(new ArrayList<>());
        }
        for(int i = 0; i<commandDto.getMenuItemCommandDtos().size(); i++){
            boolean ok = false;
            if (commandDto.getMenuItemCommandDtos().get(i).getMenuItemDto() == null) {
                LOG.error("MenuItem not found");
                throw new NotFoundException("MenuItem not found");
            }
            for(int j=0;j<command.getMenuItemCommands().size();j++){
                if(commandDto.getMenuItemCommandDtos().get(i).getMenuItemDto().getName()
                        .equals(command.getMenuItemCommands().get(j).getMenuItem().getName()))
                {
                    Integer amount = commandDto.getMenuItemCommandDtos().get(i).getAmount() +
                            command.getMenuItemCommands().get(j).getAmount();
                    command.getMenuItemCommands().get(j).setAmount(amount);
                    menuItemCommandRepository.save(command.getMenuItemCommands().get(j));
                    ok = true;
                }
            }
            if(!ok){
                command.getMenuItemCommands().add(addMenuItemCommand(command, commandDto, i));
            }
        }
        return command.getMenuItemCommands();
    }

    private MenuItemCommand addMenuItemCommand(Command command, CommandDto commandDto, int i){
        MenuItemCommand menuItemCommand = new MenuItemCommand();
        menuItemCommand.setCommand(command);
        menuItemCommand.setAmount(commandDto.getMenuItemCommandDtos().get(i).getAmount());
        MenuItem menuItem = menuItemRepository.findByName(
                commandDto.getMenuItemCommandDtos().get(i).getMenuItemDto().getName());
        if (menuItem == null) {
            LOG.error("MenuItem not found");
            throw new NotFoundException("MenuItem not found");
        }
        menuItemCommand.setMenuItem(menuItem);
        return menuItemCommandRepository.save(menuItemCommand);
    }



}
