package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.service.CommandService;

@RestController
@RequestMapping(value = "/api/command")
public class  CommandResource {

    @Autowired
    CommandService commandService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addCommand(@RequestBody CommandDto commandDto) {
        commandService.addCommand(commandDto);
    }


    // to be deleted if not used
    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public void updateCommand(@RequestBody CommandDto commandDto) {
        commandService.updateCommand(commandDto);
    }
}
