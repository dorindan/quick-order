package ro.quickorder.backend.convertors;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;

@Component
public class UserAttributeConvertor {
    
    public UserAttribute convertUserAttrDtoToUserAttribute(UserAttributeDto userAttributeDto){
        UserAttribute userAttribute = new UserAttribute();
        userAttribute.setLanguage(userAttributeDto.getLanguage());
        return userAttribute;
    }
    
}
