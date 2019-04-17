package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;

/**
 * Converts TableFoods to their corresponding DTO and vice versa.
 *
 * @author R. Lupoaie
 */

@Component
public class TableFoodConverter {

    public TableFood toTableFood(TableFoodDto tableFoodDto) {
        if (tableFoodDto == null) {
            return null;
        }
        TableFood tableFood = new TableFood();
        tableFood.setTableNr(tableFoodDto.getTableNr());
        tableFood.setSeats(tableFoodDto.getSeats());
        tableFood.setWindowView(tableFoodDto.isWindowView());
        tableFood.setFloor(tableFoodDto.getFloor());
        return tableFood;
    }

    public TableFoodDto toTableFoodDto(TableFood tableFood) {
        if (tableFood == null) {
            return null;
        }
        TableFoodDto tableFoodDto = new TableFoodDto();
        tableFoodDto.setTableNr(tableFood.getTableNr());
        tableFoodDto.setSeats(tableFood.getSeats());
        tableFoodDto.setWindowView(tableFood.isWindowView());
        tableFoodDto.setFloor(tableFood.getFloor());
        return tableFoodDto;
    }
}
