package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.PropertyConvertor;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.repository.PropertyRepository;

@Service
public class PropertyService {
    @Autowired
    PropertyConvertor propertyConvertor;
    @Autowired
    PropertyRepository propertyRepository;

    public PropertyDto findByRestaurant(String numeRestaurant) {
        Property property = propertyRepository.findByNumeRestaurant(numeRestaurant);
        return propertyConvertor.toPropertyDto(property);
    }
}
