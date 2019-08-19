package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.service.PropertyService;

@RestController
@RequestMapping(value = "/api/property")
public class PropertyResource {
    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "bistro", method = RequestMethod.GET)
    public PropertyDto getProperty() {
        return propertyService.findProperties();
    }

    @RequestMapping(value = "updateProperty", method = RequestMethod.POST)
    public PropertyDto updateProperty(@RequestBody PropertyDto propertyDto) {
        propertyService.saveProperty(propertyDto);
        return propertyDto;
    }
}
