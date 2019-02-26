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

    public UserDto(Long id, String name, String email, UserAttributeDto idAttribute) {
        this.id = id;
        this.username = name;
        this.email = email;
        this.idAttribute = idAttribute;
    }

    public UserDto(String name,String password){
        this.username = name;
        this.password = password;
    }
}
