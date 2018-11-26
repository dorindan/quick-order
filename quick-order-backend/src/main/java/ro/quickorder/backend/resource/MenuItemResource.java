package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.repository.BillRepository;
import ro.quickorder.backend.repository.MenuItemRepository;


@RestController
public class MenuItemResource {
    @Autowired
    MenuItemRepository menuItemRepository;

    @RequestMapping(path = "/menuItem", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return menuItemRepository.findById(id).toString();
    }
}
