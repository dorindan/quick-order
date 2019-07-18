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
import ro.quickorder.backend.model.MenuItemCommand;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.enumeration.CommandStatus;
import ro.quickorder.backend.repository.CommandMenuItemRepository;
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
    MenuItemService menuItemService;

    @Autowired
    MenuItemConverter menuItemConverter;

    @Autowired
    CommandRepository commandRepository;

    @Autowired
    CommandConverter commandConverter;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    CommandMenuItemConverter commandMenuItemConverter;
    @Autowired
    CommandMenuItemRepository commandMenuItemRepository;
    @Autowired
    UserRepository userRepository;

    public CommandDto userHasOpenCommand(String userName) {
        Command command = commandRepository.findActiveByUser(userRepository.findByUsername(userName), CommandStatus.ACTIVE);
        return commandConverter.toCommandDto(command);
    }

    public void updateCommand( CommandDto commandDto){
        System.out.println("Acuma incerc");
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
        for(int i=0;i<commandDto.getCommandMenuItemDtos().size();i++){
            boolean ok = false;
            for(int j=0;j<command.getMenuItemCommands().size();j++){
                if(commandDto.getCommandMenuItemDtos().get(i).getMenuItemDto().getName()
                        .equals(command.getMenuItemCommands().get(j).getMenuItem().getName()))
                {
                    Integer amount = commandDto.getCommandMenuItemDtos().get(i).getAmount() +
                            command.getMenuItemCommands().get(j).getAmount();
                    command.getMenuItemCommands().get(j).setAmount(amount);
                    commandMenuItemRepository.save(command.getMenuItemCommands().get(j));
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
        MenuItemCommand commandMenuItem = new MenuItemCommand();
        commandMenuItem.setCommand(command);
        commandMenuItem.setAmount(commandDto.getCommandMenuItemDtos().get(i).getAmount());
        commandMenuItem.setMenuItem(menuItemRepository.findByName(
                commandDto.getCommandMenuItemDtos().get(i).getMenuItemDto().getName()));
        return commandMenuItemRepository.save(commandMenuItem);
    }



}
