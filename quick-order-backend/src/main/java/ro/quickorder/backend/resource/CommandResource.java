package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.service.CommandService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/command")
public class CommandResource {
    @Autowired
    CommandService commandService;

    @RequestMapping(path = "/actual-user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<CommandDto> commandOfActualUser() {
        return commandService.getCommandsOfUser();
    }

    @RequestMapping(path = "/remove/{commandName}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('USER') or hasRole('WAITER')")
    public void removeCommand(@PathVariable String commandName) {
        commandService.removeCommand(commandName);
    }
}
