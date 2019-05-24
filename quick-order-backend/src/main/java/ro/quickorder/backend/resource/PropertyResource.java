package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.service.PropertyService;

@RestController
@RequestMapping(value = "/property")
public class PropertyResource {
    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PropertyDto getProperty() {
        return propertyService.findRestaurantProperty();
    }
}
