package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.TableFoodRepository;
import ro.quickorder.backend.service.CustomDateDeserializer;
import ro.quickorder.backend.service.TableFoodService;

import java.util.List;

@RestController
@RequestMapping(value = "/table")
public class TableFoodResource {
    @Autowired
    TableFoodRepository tableFoodRepository;
    @Autowired
    TableFoodService tableFoodService;

    @RequestMapping(path = "/table", method = RequestMethod.GET)
    public List<TableFood> findAll() {
        return tableFoodRepository.findAll();
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return tableFoodRepository.findById(id).toString();
    }

    @RequestMapping(path = "/free/{checkInTime}/{checkOutTime}", method = RequestMethod.GET)
    public List<TableFoodDto> getAllFree(@PathVariable String checkInTime, @PathVariable String checkOutTime) {
        return tableFoodService.getAllFree(CustomDateDeserializer.deserialize(checkInTime), CustomDateDeserializer.deserialize(checkOutTime));
    }
}
