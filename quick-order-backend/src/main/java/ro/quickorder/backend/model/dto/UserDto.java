package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.UserAttribute;

public class UserDto {
    private String username;
    private String password;
    private String email;
    private UserAttributeDto userAttributeDto;

    public UserDto() {
    }

    public UserDto(String username, String password, String email, UserAttributeDto userAttributeDto) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userAttributeDto = userAttributeDto;
    }

    public UserDto(String name, String email, UserAttributeDto userAttributeDto) {
        this.username = name;
        this.email = email;
        this.userAttributeDto = userAttributeDto;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAttributeDto getUserAttributeDto() {
        return userAttributeDto;
    }

    public void setUserAttributeDto(UserAttributeDto userAttributeDto) {
        this.userAttributeDto = userAttributeDto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
