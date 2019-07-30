package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.MenuItemTypeConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author R. Lupoaie
 */
@Service
public class MenuItemTypeService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);
    @Autowired
    MenuItemTypeRepository menuItemTypeRepository;
    @Autowired
    MenuItemTypeConverter menuItemTypeConverter;

    public List<MenuItemTypeDto> getAllMenuItemTypes() {
        return menuItemTypeRepository.findAll().stream()
                .map(menuItemTypeConverter::toMenuItemTypeDto)
                .collect(Collectors.toList());
    }

    public void addMenuItemType(MenuItemTypeDto menuItemTypeDto) {
        if (menuItemTypeDto.getType().length() < 2) {
            LOG.error("Item type is to short!");
            throw new BadRequestException("Item type is to short!");
        }

        if (menuItemTypeRepository.existsMenuItemTypeByType(menuItemTypeDto.getType())) {
            LOG.error("Item type already exists!");
            throw new NotFoundException("Item type already exists!");
        }
        MenuItemType menuItemType = menuItemTypeConverter.toMenuItemType(menuItemTypeDto);
        menuItemTypeRepository.save(menuItemType);
    }
}
