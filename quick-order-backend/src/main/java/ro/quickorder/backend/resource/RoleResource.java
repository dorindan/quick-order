package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.RolesDto;
import ro.quickorder.backend.service.RoleService;


@RestController
@RequestMapping("/api/role")
public class RoleResource {
    @Autowired
    RoleService roleService;

    @PreAuthorize("hasRole('WAITER')")
    @GetMapping("/all")
    public RolesDto getAllRoles() {
        return roleService.getAllRoles();
    }
}
