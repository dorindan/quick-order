package ro.quickorder.backend.model.dto;

public class UserDto {
    public Long id;
    public String username;
    public String password;
    public String email;
    public UserAttributeDto userAttributeDto;

    public UserDto(Long id) {
        this.id = id;
    }

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, UserAttributeDto userAttributeDto) {
        this.id = id;
        this.username = name;
        this.email = email;
        this.userAttributeDto = userAttributeDto;
    }

    public UserDto(String name,String password){
        this.username = name;
        this.password = password;
    }
}
