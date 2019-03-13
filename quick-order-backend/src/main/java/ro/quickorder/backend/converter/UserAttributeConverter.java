package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;

@Component
public class UserAttributeConverter {
    
    public UserAttribute convertUserAttrDtoToUserAttribute(UserAttributeDto userAttributeDto){
        UserAttribute userAttribute = new UserAttribute();
        userAttribute.setLanguage(userAttributeDto.getLanguage());
        return userAttribute;
    }

    public UserAttributeDto convertUserAttributeToUserAttrDto(UserAttribute userAttribute){
        UserAttributeDto userAttributeDto = new UserAttributeDto();
        userAttributeDto.setLanguage(userAttribute.getLanguage());
        return userAttributeDto;
    }
    
}
