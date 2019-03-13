package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.UserAttribute;

public class UserAttributeDto {

    private Language language;

    public UserAttributeDto() {
    }

    public UserAttributeDto(Language language){
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
