package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.PropertyConverter;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.PropertyName;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.repository.PropertyRepository;

import java.time.LocalTime;

@Service
public class PropertyService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    PropertyConverter propertyConvertor;
    @Autowired
    PropertyRepository propertyRepository;

    public String getRestaurantName(){
        Property restaurantNameProperty = propertyRepository.findByName(PropertyName.RESTAURANT_NAME);
        if(restaurantNameProperty == null){
            LOG.error("Restaurant name property was not found!");
            throw new NullPointerException("Restaurant name property was not found!");
        }
        return restaurantNameProperty.getValue();
    }

    public LocalTime getStartProperty(){
        Property startProperty = propertyRepository.findByName(PropertyName.START_TIME);
        if(startProperty == null){
            LOG.error("Start time property was not found!");
            throw new NullPointerException("Start time property was not found!");
        }
        int startHour;
        try {
            startHour = Integer.parseInt(startProperty.getValue());
        } catch (NumberFormatException  e){
            LOG.error("Start time is not an integer number!");
            throw new NullPointerException("Start time is not an integer number!");
        }
        return LocalTime.of(startHour,0);
    }

    public LocalTime getEndProperty(){
        Property startProperty = propertyRepository.findByName(PropertyName.END_TIME);
        if(startProperty == null){
            LOG.error("End time property was not found!");
            throw new NullPointerException("Start date property was not found!");
        }
        int endHour;
        try {
            endHour = Integer.parseInt(startProperty.getValue());
        } catch (NumberFormatException  e){
            LOG.error("End time is not an integer number!");
            throw new NullPointerException("End time is not an integer number!");
        }
        return LocalTime.of(endHour,0);
    }

    public String getStreetName(){
        Property streetNameProperty = propertyRepository.findByName(PropertyName.STREET_NAME);
        if(streetNameProperty == null){
            LOG.error("Street name property was not found!");
            throw new NullPointerException("Street name property was not found!");
        }
        return streetNameProperty.getValue();
    }

    public double getLocationLatitude(){
        Property locationLatitude = propertyRepository.findByName(PropertyName.LOCATION_LATITUDE);
        if(locationLatitude == null){
            LOG.error("Latitude property was not found!");
            throw new NullPointerException("Latitude property was not found!");
        }
        double latitude;
        try {
            latitude = Double.parseDouble(locationLatitude.getValue());
        } catch (NumberFormatException  e){
            LOG.error("Latitude is not a double number!");
            throw new NullPointerException("Latitude is not a double number!");
        }
        return latitude;
    }

    public double getLocationLongitude(){
        Property locationLongitude = propertyRepository.findByName(PropertyName.LOCATION_LONGITUDE);
        if(locationLongitude == null){
            LOG.error("Longitude property was not found!");
            throw new NullPointerException("Longitude property was not found!");
        }
        double longitude;
        try {
            longitude = Double.parseDouble(locationLongitude.getValue());
        } catch (NumberFormatException  e){
            LOG.error("Longitude is not a double number!");
            throw new NullPointerException("Longitude is not a double number!");
        }
        return longitude;
    }

    public String getEmail(){
        Property emailProperty = propertyRepository.findByName(PropertyName.EMAIL);
        if(emailProperty == null){
            LOG.error("Email property was not found!");
            throw new NullPointerException("Email property was not found!");
        }
        return emailProperty.getValue();
    }

    public PropertyDto findProperties() {
        return new PropertyDto(getRestaurantName(),getStartProperty(),getEndProperty(),getStreetName(),getLocationLatitude(),getLocationLongitude(),getEmail());
    }
}
