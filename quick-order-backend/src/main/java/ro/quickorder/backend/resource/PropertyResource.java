package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.repository.PropertyRepository;
import ro.quickorder.backend.repository.UserRepository;
import ro.quickorder.backend.service.PropertyService;

import java.util.List;

@RestController
@RequestMapping(value = "/property")
public class PropertyResource {

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "bistro",method = RequestMethod.GET)
    public PropertyDto getBistroProperty(){
        return propertyService.findByRestaurant("Bistro");
    }

}
