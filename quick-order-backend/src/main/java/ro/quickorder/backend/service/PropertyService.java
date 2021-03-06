package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.model.enumeration.PropertyName;
import ro.quickorder.backend.repository.PropertyRepository;

import java.time.LocalTime;

@Service
public class PropertyService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    PropertyRepository propertyRepository;

    public String getPropertyByName(PropertyName propertyName) {
        if (propertyName == null) {
            return null;
        }
        final Property property = propertyRepository.findByName(propertyName);
        if (property == null) {
            LOG.error("property {} was not found!", propertyName);
            return null;
        }
        return property.getValue();
    }

    public String getRestaurantName() {
        return getPropertyByName(PropertyName.RESTAURANT_NAME);
    }

    public LocalTime getStartProperty() {
        String startPropertyValue = getPropertyByName(PropertyName.START_TIME);
        int startHour;
        try {
            startHour = Integer.parseInt(startPropertyValue);
        } catch (NumberFormatException e) {
            LOG.error("Start time is not an integer number!");
            throw new NullPointerException("Start time is not an integer number!");
        }
        return LocalTime.of(startHour, 0);
    }

    public LocalTime getEndProperty() {
        String startPropertyValue = getPropertyByName(PropertyName.END_TIME);
        int endHour;
        try {
            endHour = Integer.parseInt(startPropertyValue);
        } catch (NumberFormatException e) {
            LOG.error("End time is not an integer number!");
            throw new NullPointerException("End time is not an integer number!");
        }
        return LocalTime.of(endHour, 0);
    }

    public String getStreetName() {
        return getPropertyByName(PropertyName.STREET_NAME);
    }

    public double getLocationLatitude() {
        String locationLatitudeValue = getPropertyByName(PropertyName.LOCATION_LATITUDE);
        double latitude;
        try {
            latitude = Double.parseDouble(locationLatitudeValue);
        } catch (NumberFormatException e) {
            LOG.error("Latitude is not a double number!");
            throw new NullPointerException("Latitude is not a double number!");
        }
        return latitude;
    }

    public double getLocationLongitude() {
        String locationLongitudeValue = getPropertyByName(PropertyName.LOCATION_LONGITUDE);
        double longitude;
        try {
            longitude = Double.parseDouble(locationLongitudeValue);
        } catch (NumberFormatException e) {
            LOG.error("Longitude is not a double number!");
            throw new NullPointerException("Longitude is not a double number!");
        }
        return longitude;
    }

    public String getEmail() {
        return getPropertyByName(PropertyName.EMAIL);
    }

    public PropertyDto findProperties() {
        return new PropertyDto.Builder().withRestaurantName(getRestaurantName()).withStartProgramTime(getStartProperty()).
                withEndProgramTime(getEndProperty()).withStreetName(getStreetName()).
                withLatitude(getLocationLatitude()).withLongitude(getLocationLongitude()).
                withEmail(getEmail()).build();
    }

    public void saveProperty(PropertyDto propertyDto) {
        Property nameProperty = propertyRepository.findByName(PropertyName.RESTAURANT_NAME);
        nameProperty.setValue(propertyDto.getRestaurantName());
        propertyRepository.save(nameProperty);

        Property startTimeProperty = propertyRepository.findByName(PropertyName.START_TIME);
        startTimeProperty.setValue(String.valueOf(propertyDto.getStartProgramTime().getHour()));
        propertyRepository.save(startTimeProperty);

        Property endTimeProperty = propertyRepository.findByName(PropertyName.END_TIME);
        endTimeProperty.setValue(String.valueOf(propertyDto.getEndProgramTime().getHour()));
        propertyRepository.save(endTimeProperty);

        Property streetNameProperty = propertyRepository.findByName(PropertyName.STREET_NAME);
        streetNameProperty.setValue(propertyDto.getStreetName());
        propertyRepository.save(streetNameProperty);

        Property locationLatitudeProperty = propertyRepository.findByName(PropertyName.LOCATION_LATITUDE);
        locationLatitudeProperty.setValue(String.valueOf(propertyDto.getLatitude()));
        propertyRepository.save(locationLatitudeProperty);

        Property locationLongitudeproperty = propertyRepository.findByName(PropertyName.LOCATION_LONGITUDE);
        locationLongitudeproperty.setValue(String.valueOf(propertyDto.getLongitude()));
        propertyRepository.save(locationLongitudeproperty);

        Property emailProperty = propertyRepository.findByName(PropertyName.EMAIL);
        emailProperty.setValue(propertyDto.getEmail());
        propertyRepository.save(emailProperty);
    }
}
