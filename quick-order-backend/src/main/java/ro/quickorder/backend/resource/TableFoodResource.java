package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.service.TableFoodService;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping(value = "/api/table")
public class TableFoodResource {
    @Autowired
    TableFoodService tableFoodService;

    @RequestMapping(path = "/free/{checkInTime}/{checkOutTime}", method = RequestMethod.GET)
    public List<TableFoodDto> getAllFree(@PathVariable String checkInTime, @PathVariable String checkOutTime) {
        return tableFoodService.getAllFree(checkInTime, checkOutTime);
    }

    @RequestMapping(path = "/free/{reservationName}", method = RequestMethod.GET)
    public List<TableFoodDto> getAllAssignedTablesOfAReservation(@PathVariable String reservationName) {
        return tableFoodService.getAllAssignedTablesOfAReservation(reservationName);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<TableFoodDto> getAllTables() {
        return tableFoodService.getAll();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addTable(@RequestBody @NotNull TableFoodDto tableFoodDto) {
        tableFoodService.addTable(tableFoodDto);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public void updateTable(@RequestBody @NotNull TableFoodDto tableFoodDto) {
        tableFoodService.updateTable(tableFoodDto);
    }

    @RequestMapping(path = "/remove/{tableNr}", method = RequestMethod.DELETE)
    public void removeTable(@PathVariable int tableNr) {
        tableFoodService.removeTable(tableNr);
    }
}
