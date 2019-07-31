package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.CommandConverter;
import ro.quickorder.backend.converter.MenuItemCommandConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.*;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.dto.MenuItemCommandDto;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.model.enumeration.CommandStatus;
import ro.quickorder.backend.repository.MenuItemCommandRepository;
import ro.quickorder.backend.repository.CommandRepository;
import ro.quickorder.backend.repository.MenuItemRepository;
import ro.quickorder.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public CommandDto addCommand(CommandDto commandDto) {
        Command savedCommand = mapCommandEntityFromCommandDto(commandDto);
        // save and set MenuItemCommand
        for(MenuItemCommandDto menuItemCommandDto : commandDto.getMenuItemCommandDtos() ){
            MenuItemCommand savedMenuItemCommand = saveMenuItemCommand( savedCommand, menuItemCommandDto);
            savedCommand.getMenuItemCommands().add(savedMenuItemCommand);
        }
        return commandConverter.toCommandDto(commandRepository.save(savedCommand));
    }

    private MenuItemCommand saveMenuItemCommand(Command savedCommand, MenuItemCommandDto menuItemCommandDto){
        savedCommand.setMenuItemCommands(new ArrayList<>());
        MenuItem menuItem = menuItemRepository.findByName(menuItemCommandDto.getMenuItemDto().getName());
        if (menuItem == null) {
            LOG.error("Item not found");
            throw new NotFoundException("Item not found");
        }
        MenuItemCommand menuItemCommand = new MenuItemCommand();
        menuItemCommand.setMenuItem(menuItem);
        menuItemCommand.setCommand(savedCommand);
        menuItemCommand.setAmount(menuItemCommandDto.getAmount());
        return menuItemCommandRepository.save(menuItemCommand);
    }

    private Command mapCommandEntityFromCommandDto(CommandDto commandDto){
        Command command = new Command();
        // find user
        User user = userRepository.findByUsername(commandDto.getUserDto().getUsername());
        if (user == null) {
            LOG.error("User not found");
            throw new NotFoundException("User not found");
        }
        // set command
        command.setUser(user);
        command.setCommandName(UUID.randomUUID().toString());
        command.setStatus(CommandStatus.DONE);
        command.setSpecification(commandDto.getSpecification());
        command.setPacked(commandDto.isPacked());
        return commandRepository.save(command);
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
        Command receivedCommand = commandConverter.toCommand(receivedCommandDto);
        command.setMenuItemCommands(combineMenuItemCommands(command, receivedCommand));
        commandRepository.save(command);
    }

    private List<MenuItemCommand> combineMenuItemCommands(Command existingCommand, Command receivedCommand){
        if (existingCommand.getMenuItemCommands() == null) {
            existingCommand.setMenuItemCommands(new ArrayList<>());
        }
        for (int i = 0; i < receivedCommand.getMenuItemCommands().size(); i++) {
            boolean ok = false;
            if (receivedCommand.getMenuItemCommands().get(i).getMenuItem() == null) {
                LOG.error("MenuItem not found");
                throw new NotFoundException("MenuItem not found");
            }
            for (int j = 0; j < existingCommand.getMenuItemCommands().size(); j++) {
                String receivedMenuItemDto = receivedCommand.getMenuItemCommands().get(i).getMenuItem().getName();
                String existingMenuItemDto = existingCommand.getMenuItemCommands().get(j).getMenuItem().getName();
                if (receivedMenuItemDto.equals(existingMenuItemDto)) {
                    Integer amount = receivedCommand.getMenuItemCommands().get(i).getAmount() +
                            existingCommand.getMenuItemCommands().get(j).getAmount();
                    existingCommand.getMenuItemCommands().get(j).setAmount(amount);
                    saveMenuItemCommandDto(existingCommand.getMenuItemCommands().get(j), existingCommand);
                    ok = true;
                }
            }
            if (!ok) {
                existingCommand.getMenuItemCommands().add(saveMenuItemCommandDto(receivedCommand.getMenuItemCommands().get(i), receivedCommand));
            }
        }

        return existingCommand.getMenuItemCommands();
    }

    private MenuItemCommand saveMenuItemCommandDto(MenuItemCommand menuItemCommand, Command existingCommand){
        Command command = commandRepository.findByCommandName(existingCommand.getCommandName());
        MenuItem menuItem = menuItemRepository.findByName(menuItemCommand.getMenuItem().getName());
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
       return menuItemCommandRepository.save(menuItemCommand);
    }

    public List<CommandDto> getCommandsOfUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        if (username == null) {
            LOG.info("User not authenticated");
            return null;
        } else {
            LOG.info("Commands of user: " + username + " requested.");

            final List<Command> commands = commandRepository.findAll()
                    .stream()
                    .filter(command -> {
                        for (User user :
                                command.getUsers()) {
                            if (user.getUsername().equals(username)) {
                                return true;
                            }
                        }
                        return false;
                    }).collect(Collectors.toList());

            return commands.stream()
                    .map(commandConverter::toCommandDto)
                    .collect(Collectors.toList());

        }
    }

    public void removeCommand(String commandName) {
        final Command command = commandRepository.findByCommandName(commandName);
        if (command == null) {
            LOG.error("Command with name: " + commandName + " was not found!");
            throw new NotFoundException("Command not found!");
        }
        commandRepository.delete(command);
    }

    public List<CommandDto> getCommandsWithStatus(String status) {
        return commandRepository.findCommandsByStatus(status)
                .stream()
                .map(commandConverter::toCommandDto)
                .collect(Collectors.toList());
    }

    public void confirmCommand(CommandDto commandDto) {
        String commandName = commandDto.getCommandName();
        final Command command = commandRepository.findByCommandName(commandName);
        if (command == null) {
            LOG.error("Command with name: " + commandName + " was not found!");
            throw new NotFoundException("Command not found!");
        }
        command.setStatus("confirmed");
        commandRepository.save(command);
    }
}
