package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.MenuItemTypeConverter;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import java.util.ArrayList;
import java.util.List;

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
        List<MenuItemTypeDto> menuItemTypes = new ArrayList<>();
        menuItemTypeRepository.findAll().stream().map(menuItemType -> menuItemTypeConverter.toMenuItemTypeDto(menuItemType)).forEach(menuItemTypes::add);
        return menuItemTypes;
    }
}
