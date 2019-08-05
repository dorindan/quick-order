package ro.quickorder.backend.model.dto;

import java.util.Set;

public class RolesDto {
    Set<String> roles;

    public RolesDto() {
    }

    public RolesDto(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
