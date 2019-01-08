package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.repository.TableFoodRepository;
import java.util.List;


@RestController
public class TableFoodResource {
    @Autowired
    TableFoodRepository tableFoodRepository;

    @RequestMapping(path = "/table", method = RequestMethod.GET)
    public List<TableFood> findAll() {
        return tableFoodRepository.findAll();
    }

}
