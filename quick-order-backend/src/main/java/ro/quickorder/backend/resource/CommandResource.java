package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.enumeration.CommandStatus;
import ro.quickorder.backend.service.CommandService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/command")
public class CommandResource {
    @Autowired
    CommandService commandService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public CommandDto addCommand(@RequestBody CommandDto commandDto) {
        return commandService.addCommand(commandDto);
    }


    // to be deleted if not used
    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public void updateCommand(@RequestBody CommandDto commandDto) {
        commandService.updateCommand(commandDto);
    }

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

    @RequestMapping(path = "/unconfirmed", method = RequestMethod.GET)
    @PreAuthorize("hasRole('WAITER')")
    public List<CommandDto> getAllUnconfirmed() {
        return commandService.getCommandsWithStatus(CommandStatus.DONE);
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('WAITER')")
    public void confirmReservation(@RequestBody CommandDto commandDto) {
        commandService.confirmCommand(commandDto);
    }

    @RequestMapping(path = "/all-from-command", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('WAITER')")
    public CommandDto updateMenuItemsFromCommand(@RequestBody CommandDto commandDto) {
        return commandService.updateMenuItemsFromCommand(commandDto);
    }
}
