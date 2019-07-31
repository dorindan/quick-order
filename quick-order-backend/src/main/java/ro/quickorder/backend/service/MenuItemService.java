package ro.quickorder.backend.service;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.converter.MenuItemTypeConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.repository.IngredientRepository;
import ro.quickorder.backend.repository.MenuItemRepository;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author R. Lupoaie
 */
@Service
public class MenuItemService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuItemTypeRepository menuItemTypeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private MenuItemConverter menuItemConverter;

    public List<MenuItemDto> getMenuItems() {
        return menuItemRepository.findAll().stream().map(menuItemConverter::toMenuItemDto).collect(Collectors.toList());
    }

    public void addMenuItem(MenuItemDto menuItemDto) {
        if (menuItemDto.getName() == null) {
            LOG.error("Name can not be null");
            throw new BadRequestException("Name can not be null");
        }
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if (menuItem != null) {
            LOG.error("MenuItem already exists!");
            throw new BadRequestException("MenuItem already exists!");
        }
        MenuItemType menuItemType = menuItemTypeRepository.findByType(menuItemDto.getMenuItemTypeDto().getType());
        if (menuItemType == null) {
            LOG.error("MenuItemType was not found!");
            throw new NotFoundException("MenuItemType was not found!");
        }

        Set<Ingredient> ingredients = setIngredients(menuItemDto);
        menuItem = new MenuItem(menuItemDto.getName(), menuItemDto.getDescription(), menuItemType, menuItemDto.getPreparationDurationInMinutes(), menuItemDto.getPrice(), ingredients);
        menuItemRepository.save(menuItem);
    }

    public void uploadImg(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {

            final String dir = System.getProperty("user.dir");
            String location = "\\src\\assets\\menuItemImg\\";
            String orgName = multipartFile.getOriginalFilename();
            String filePath = dir + location + orgName;
            File dest = new File(filePath);
            if (dest.exists() && !dest.isDirectory()) {
                dest.delete();
            }
            try {
                multipartFile.transferTo(dest);
            } catch (IOException e) {
                throw new BadRequestException("The file could not be added!");
            }
        }
    }

    public void updateMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if (menuItem == null) {
            LOG.error("MenuItem not found!");
            throw new NotFoundException("MenuItem not found!");
        }
        MenuItemType menuItemType = menuItemTypeRepository.findByType(menuItemDto.getMenuItemTypeDto().getType());
        if (menuItemType == null) {
            LOG.error("MenuItemType was not found!");
            throw new NotFoundException("MenuItemType was not found!");
        }

        Set<Ingredient> ingredients = setIngredients(menuItemDto);
        menuItem.setIngredients(ingredients);
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setMenuItemType(menuItemType);
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());
        menuItemRepository.save(menuItem);
    }

    private Set<Ingredient> setIngredients(MenuItemDto menuItemDto) {
        Set<Ingredient> ingredients = new HashSet<>();
        if (menuItemDto.getIngredients() != null) {
            menuItemDto.getIngredients().forEach(ingredientDto -> {
                Ingredient ingredient = ingredientRepository.findFirstByName(ingredientDto.getName());
                if (ingredient != null) {
                    ingredients.add(ingredient);
                } else {
                    LOG.error("Ingredient " + ingredient.toString() + " was not found!");
                    throw new NotFoundException("Ingredient " + ingredient.toString() + " was not found!");
                }
            });
        }
        return ingredients;
    }

    public void removeMenuItem(String menuItemName) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemName);
        if (menuItem == null) {
            LOG.error("MenuItem not found!");
            throw new NotFoundException("MenuItem not found!");
        }
        menuItemRepository.delete(menuItem);
    }

}
