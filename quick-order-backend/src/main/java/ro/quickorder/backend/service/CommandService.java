package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.CommandConverter;
import ro.quickorder.backend.converter.MenuItemCommandConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemCommand;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.dto.MenuItemCommandDto;
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
    @Autowired
    private MenuItemCommandConverter menuItemCommandConverter;

    public CommandDto getUserActiveCommand(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            LOG.error("User not found");
            throw new NotFoundException("User not found");
        }
        Command command = commandRepository.findActiveByUser(user, CommandStatus.ACTIVE);
        return commandConverter.toCommandDto(command);
    }

    public void updateCommand(CommandDto receivedCommandDto) {
        Command command = commandRepository.findByCommandNameWithItems(receivedCommandDto.getCommandName());
        if (command == null) {
            LOG.error("Command not found");
            throw new NotFoundException("Command not found");
        }
        command.setStatus(receivedCommandDto.getStatus());
        command.setPacked(receivedCommandDto.isPacked());
        command.setSpecification(receivedCommandDto.getSpecification());

        CommandDto commandDto = commandConverter.toCommandDto(command);
        command.setMenuItemCommands(menuItemCommandConverter.toMenuItemCommands(combineMenuItemCommands(commandDto, receivedCommandDto)));

        commandRepository.save(command);
    }

    private List<MenuItemCommandDto> combineMenuItemCommands(CommandDto existingCommandDto, CommandDto receivedCommandDto){
        if (existingCommandDto.getMenuItemCommandDtos() == null) {
            existingCommandDto.setMenuItemCommandDtos(new ArrayList<>());
        }
        for (int i = 0; i < receivedCommandDto.getMenuItemCommandDtos().size(); i++) {
            boolean ok = false;
            if (receivedCommandDto.getMenuItemCommandDtos().get(i).getMenuItemDto() == null) {
                LOG.error("MenuItem not found");
                throw new NotFoundException("MenuItem not found");
            }
            for (int j = 0; j < existingCommandDto.getMenuItemCommandDtos().size(); j++) {
                String receivedMenuItemDto = receivedCommandDto.getMenuItemCommandDtos().get(i).getMenuItemDto().getName();
                String existingMenuItemDto = existingCommandDto.getMenuItemCommandDtos().get(j).getMenuItemDto().getName();
                if (receivedMenuItemDto.equals(existingMenuItemDto)) {
                    Integer amount = receivedCommandDto.getMenuItemCommandDtos().get(i).getAmount() +
                            existingCommandDto.getMenuItemCommandDtos().get(j).getAmount();
                    existingCommandDto.getMenuItemCommandDtos().get(j).setAmount(amount);
                    saveMenuItemCommandDto(existingCommandDto.getMenuItemCommandDtos().get(j), existingCommandDto);
                    ok = true;
                }
            }
            if (!ok) {
                MenuItemCommandDto menuItemCommandDto = saveMenuItemCommandDto(receivedCommandDto.getMenuItemCommandDtos().get(i), receivedCommandDto);
                existingCommandDto.getMenuItemCommandDtos().add(menuItemCommandDto);
            }
        }

        return existingCommandDto.getMenuItemCommandDtos();
    }

    private MenuItemCommandDto saveMenuItemCommandDto(MenuItemCommandDto menuItemCommandDto, CommandDto commandDto){
        MenuItemCommand menuItemCommand = menuItemCommandConverter.toMenuItemCommand(menuItemCommandDto);
        Command command = commandRepository.findByCommandName(commandDto.getCommandName());
        MenuItem menuItem = menuItemRepository.findByName(menuItemCommandDto.getMenuItemDto().getName());
        if (command == null) {
            LOG.error("Command not found");
            throw new NotFoundException("Command not found");
        }
        menuItemCommand.setCommand(command);
        if (menuItem == null) {
            LOG.error("MenuItem not found");
            throw new NotFoundException("MenuItem not found");
        }
        menuItemCommand.setMenuItem(menuItem);
       return menuItemCommandConverter.toMenuItemCommandDto(menuItemCommandRepository.save(menuItemCommand));
    }
}
