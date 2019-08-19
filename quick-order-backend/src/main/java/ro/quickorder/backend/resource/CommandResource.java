package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.service.CommandService;

@RestController
@RequestMapping(value = "/api/command")
public class  CommandResource {

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

    @RequestMapping(path = "/all-from-command", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('WAITER')")
    public CommandDto updateMenuItemsFromCommand(@RequestBody CommandDto commandDto) {
        return commandService.updateMenuItemsFromCommand(commandDto);
    }
}
