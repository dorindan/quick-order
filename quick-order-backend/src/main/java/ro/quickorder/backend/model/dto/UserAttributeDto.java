package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.UserAttribute;

public class UserAttributeDto {
    public Long userId;
    public Language language;

    public UserAttributeDto() {
    }

    public UserAttributeDto(UserAttribute userAttribute) {
        this.language = userAttribute.getLanguage();
        this.userId = userAttribute.getUser().getId();
    }
}
