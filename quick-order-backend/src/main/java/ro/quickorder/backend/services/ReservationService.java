package ro.quickorder.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableFoodRepository tableFoodRepository;

    public List<ReservationDto> getAllUnconfirmed() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDto> results = new ArrayList<>();

        for (Reservation res : reservations) {
            if (!res.isConfirmed()) {
                results.add(new ReservationDto(res));
            }
        }
        return results;
    }

    public void confirmReservation(ReservationDto reservationDto,  List<TableFoodDto> tableFoodDtos) {

        if (reservationDto.reservationName == null)
            throw new NotFoundException("Reservation not found");

        // find reservation
        Reservation reservation = getReservationEntityByName(reservationDto);

        // find tables
        List<TableFood> reservationTables = getTablesByName(tableFoodDtos);

        // occupy all table
        occupyAllTable(reservationTables);

        // put tables in reservation
        reservation.setTables(reservationTables);
        reservation.setConfirmed(true);

        // save reservation in database
        reservationRepository.save(reservation);

    }

    private Reservation getReservationEntityByName(ReservationDto reservationDto)
    {
        // find reservation
        Reservation reservation = reservationRepository.findByReservationName(reservationDto.reservationName);
        if(reservation.isConfirmed())
            throw new NotFoundException("Reservation is already confirmed");
        if (reservation == null)
            throw new NotFoundException("Reservation not found");
        return reservation;
    }

    private List<TableFood> getTablesByName(List<TableFoodDto> tableFoodDtos){
        if (tableFoodDtos.size() == 0)
            throw new ForbiddenException("TableList can not be null");
        List<TableFood> tableFoodListToSet = new ArrayList<>();
        for (TableFoodDto tableFoodDto : tableFoodDtos) {
            TableFood tableFood = tableFoodRepository.findByTableNr(tableFoodDto.tableNr);
            if (tableFood == null || !tableFood.isFree())
                throw new NotFoundException("Table not found");
            tableFoodListToSet.add(tableFood);
        }
        return tableFoodListToSet;
    }

    private void occupyAllTable(List<TableFood> tableFoodListToSet){
        for (TableFood table : tableFoodListToSet) {
            table.setFree(false);
            tableFoodRepository.save(table);
        }
    }

}
