package ro.quickorder.backend.convertors;

import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;

public class UserAttributeConvertor {
    
    public UserAttribute convertUserAttrDtoToUserAttribute(UserAttributeDto userAttributeDto){
        UserAttribute userAttribute = new UserAttribute();
        userAttribute.setLanguage(userAttributeDto.language);
        return userAttribute;
    }
    
}
