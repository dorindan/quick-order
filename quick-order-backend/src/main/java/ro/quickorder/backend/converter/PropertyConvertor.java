package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.dto.PropertyDto;

@Component
public class PropertyConvertor {

    public Property toProperty(PropertyDto propertyDto) {
        if (propertyDto == null) {
            return null;
        }
        Property property = new Property();
        property.setEndProgramTime(propertyDto.getEndProgramTime());
        property.setStartProgramTime(propertyDto.getStartProgramTime());
        return property;
    }

    public PropertyDto toPropertyDto(Property property) {
        if (property == null) {
            return null;
        }
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setEndProgramTime(property.getEndProgramTime());
        propertyDto.setStartProgramTime(property.getStartProgramTime());
        return propertyDto;
    }
}
