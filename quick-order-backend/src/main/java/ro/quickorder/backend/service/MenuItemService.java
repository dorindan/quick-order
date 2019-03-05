package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.repository.MenuItemRepository;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    MenuItemTypeRepository menuItemTypeRepository;

    public Object getMenu(){
        return null;
    }
}
