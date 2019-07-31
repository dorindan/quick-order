package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.RoleConverter;
import ro.quickorder.backend.model.Role;
import ro.quickorder.backend.model.dto.RolesDto;
import ro.quickorder.backend.repository.RoleRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleConverter roleConverter;

    public RolesDto getAllRoles() {
        Set<Role> roles = roleRepository.findAll().stream().collect(Collectors.toSet());
        RolesDto rolesDto = new RolesDto(roleConverter.toStringSet(roles));
        return rolesDto;
    }
}
