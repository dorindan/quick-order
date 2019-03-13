package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;

@Component
public class UserAttributeConverter {

    public UserAttribute toUserAttribute(UserAttributeDto userAttributeDto) {
        if (userAttributeDto == null)
            return null;
        UserAttribute userAttribute = new UserAttribute();
        userAttribute.setLanguage(userAttributeDto.getLanguage());
        return userAttribute;
    }

    public UserAttributeDto toUserAttributeDto(UserAttribute userAttribute) {
        if (userAttribute == null)
            return null;
        UserAttributeDto userAttributeDto = new UserAttributeDto();
        userAttributeDto.setLanguage(userAttribute.getLanguage());
        return userAttributeDto;
    }

}
