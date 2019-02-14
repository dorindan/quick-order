package ro.quickorder.backend.model.dto;

public class UserDto {
    public Long id;
    public String username;
    public String password;
    public String email;
    public UserAttributeDto idAttribute;

    public UserDto(Long id) {
        this.id = id;
    }

    public UserDto() {
    }

    public UserDto(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
    }
}
