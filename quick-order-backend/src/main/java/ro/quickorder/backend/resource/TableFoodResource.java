package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.repository.TableFoodRepository;


@RestController
@RequestMapping(value = "/api")
public class TableFoodResource {
    @Autowired
    TableFoodRepository tableFoodRepository;

    @RequestMapping(path = "/table", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return tableFoodRepository.findById(id).toString();
    }
}
