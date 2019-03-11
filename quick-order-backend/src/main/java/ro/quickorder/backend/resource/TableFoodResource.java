package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.TableFoodRepository;
import ro.quickorder.backend.services.TableFoodService;

import java.util.List;


@RestController
@RequestMapping(value = "/table")
public class TableFoodResource {

    @Autowired
    TableFoodRepository tableFoodRepository;
    @Autowired
    TableFoodService tableFoodService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return tableFoodRepository.findById(id).toString();
    }

    @RequestMapping(path = "/free", method = RequestMethod.GET)
    public List<TableFoodDto> getAllFree(){
        return tableFoodService.getAllFree();
    }

}
