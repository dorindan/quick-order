package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.PropertyConvertor;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.repository.PropertyRepository;

import java.util.List;

@Service
public class PropertyService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);
    @Autowired
    PropertyConvertor propertyConvertor;
    @Autowired
    PropertyRepository propertyRepository;

    public PropertyDto findRestaurantProperty() {
        List<Property> property = propertyRepository.findAll();
        if(property.size()>1){
            LOG.error("There are to many properties!");
            throw new NotFoundException("There are to many properties!");
        }
        if(property.size()<1){
            LOG.error("There is no property!");
            throw new NotFoundException("There is no property!");
        }
        return propertyConvertor.toPropertyDto(property.get(0));
    }
}
