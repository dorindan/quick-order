package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.repository.MenuItemTypeRepository;
import ro.quickorder.backend.repository.UsersRepository;


@RestController
public class MenuItemTypeResource {
    @Autowired
    MenuItemTypeRepository menuItemTypeRepository;

    @RequestMapping(path = "/menuItemType", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return menuItemTypeRepository.findById(id).toString();
    }
}
