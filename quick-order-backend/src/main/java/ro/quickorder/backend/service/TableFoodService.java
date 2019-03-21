package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.TableFoodConverter;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableFoodService {

    @Autowired
    private TableFoodConverter tableFoodConverter;

    @Autowired
    private TableFoodRepository tableFoodRepository;



    public List<TableFoodDto> getAllFree(){

        List<TableFoodDto> rez = new ArrayList<>();
        List<TableFood> tables = tableFoodRepository.findAll();
        for (TableFood table: tables)
            if(table.isFree())
                rez.add(tableFoodConverter.toTableFoodDto(table));


        return rez;
    }

    public List<TableFoodDto> getAll(){
        List<TableFoodDto> rez = new ArrayList<>();
        List<TableFood> tables = tableFoodRepository.findAll();
        for (TableFood table: tables)
                rez.add(tableFoodConverter.toTableFoodDto(table));
        return rez;
    }

}
