package ro.quickorder.backend.model.dto;

public class UserDto {
    public String username;
    public String password;
    public String email;
    public UserAttributeDto userAttributeDto;

    public UserDto() {
    }

    public UserDto(String name, String email, UserAttributeDto userAttributeDto) {
        this.username = name;
        this.email = email;
        this.userAttributeDto = userAttributeDto;
    }

    public UserDto(String name,String password){
        this.username = name;
        this.password = password;
    }
}
