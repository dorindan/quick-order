package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.CommandConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.repository.CommandRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    CommandRepository commandRepository;
    @Autowired
    CommandConverter commandConverter;

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
}
