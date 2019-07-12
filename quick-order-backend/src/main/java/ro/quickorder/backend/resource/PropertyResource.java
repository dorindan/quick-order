package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.ProgramDto;
import ro.quickorder.backend.service.PropertyService;

@RestController
@RequestMapping(value = "/api/property")
public class PropertyResource {
    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "bistro", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('WAITER')")
    public ProgramDto getProperty() {
        return propertyService.findSchedule();
    }
}
