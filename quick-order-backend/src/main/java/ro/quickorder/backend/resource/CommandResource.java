package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.service.CommandService;

@RestController
@RequestMapping(value = "/api/command")
public class CommandResource {

    @Autowired
    CommandService commandService;

    @RequestMapping(path = "/hasCommand/{userName}", method = RequestMethod.GET)
    public CommandDto getUserActiveCommand(@PathVariable String userName) {
        return commandService.getUserActiveCommand(userName);
    }

    // to be deleted if not used
    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public void updateCommand(@RequestBody CommandDto commandDto) {
        commandService.updateCommand(commandDto);
    }
}
