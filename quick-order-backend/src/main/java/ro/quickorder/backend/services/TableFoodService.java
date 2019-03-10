package ro.quickorder.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableFoodService {

    @Autowired
    private TableFoodRepository tableFoodRepository;

    public List<TableFoodDto> getAllTableFood(){

        List<TableFoodDto> rez = new ArrayList<>();
        List<TableFood> tableFoodList = tableFoodRepository.findAll();
        for (TableFood table: tableFoodList)
            if(table.isFree())
                rez.add(new TableFoodDto(table));


        return rez;
    }

}
