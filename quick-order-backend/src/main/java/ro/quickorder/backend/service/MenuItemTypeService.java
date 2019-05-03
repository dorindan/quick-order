package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.MenuItemTypeConverter;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author R. Lupoaie
 */
@Service
public class MenuItemTypeService {
    @Autowired
    MenuItemTypeRepository menuItemTypeRepository;
    @Autowired
    MenuItemTypeConverter menuItemTypeConverter;

    public List<MenuItemTypeDto> getAllMenuItemTypes() {
        return menuItemTypeRepository.findAll().stream()
                .map(menuItemTypeConverter::toMenuItemTypeDto)
                .collect(Collectors.toList());
    }
}
