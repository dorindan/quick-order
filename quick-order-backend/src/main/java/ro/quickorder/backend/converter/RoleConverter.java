package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Role;
import ro.quickorder.backend.model.enumeration.RoleName;
import ro.quickorder.backend.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleConverter {
    @Autowired
    private RoleRepository roleRepository;

    public RoleConverter() {
    }

    public Set<Role> toRoleSet(Set<String> rolesAsStrings) {
        Set<Role> roles = new HashSet<>();
        rolesAsStrings.stream().map(RoleName::from)
                .map(role -> roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Could not find Role " + role.name())))
                .forEach(roles::add);
        return roles;
    }

    public Set<String> toStringSet(Set<Role> roles) {
        Set<String> rolesAsString = new HashSet<>();
        roles.stream().map(Role::getName).map(RoleName::fromEnumToString).forEach(rolesAsString::add);
        return rolesAsString;
    }

}
