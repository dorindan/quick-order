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
        List<ReservationDto> result = new ArrayList<>();

        for (Reservation res : reservations) {
            if (!res.isConfirmed()) {
                result.add(new ReservationDto(res));
            }
        }
        return result;
    }

    public void confirmReservation(ReservationDto reservationDto,  List<TableFoodDto> tableFoodDtos) {

        if (reservationDto.reservationName == null)
            throw new NotFoundException("Reservation not found");

        // find reservation
        Reservation reservation = null;
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation res : reservations) {
            if (res.getReservationName().equals(reservationDto.reservationName)) {
                if(res.isConfirmed())
                    throw new NotFoundException("Reservation is already confirmed");
                reservation = res;
            }
        }
        if (reservation == null)
            throw new NotFoundException("Reservation not found");

        // find tables
        if (tableFoodDtos.size() == 0)
            throw new ForbiddenException("TableList can not be null");
        List<TableFood> tableFoodListToSet = new ArrayList<>();
        List<TableFood> tableFoodList = tableFoodRepository.findAll();
        for (TableFoodDto tableFoodDto : tableFoodDtos) {
            TableFood tableFood = null;
            for (TableFood table : tableFoodList) {
                if (table.getTableNr() == tableFoodDto.tableNr) {
                    if(!table.isFree())
                        throw new NotFoundException("Table is already taken!");
                    tableFood = table;
                }
            }
            if (tableFood == null)
                throw new NotFoundException("Reservation not found");
            tableFoodListToSet.add(tableFood);
        }


        for (TableFood table : tableFoodListToSet) {
            table.setFree(false);
            tableFoodRepository.save(table);
        }

        // put tables in reservation
        reservation.setTables(tableFoodListToSet);
        reservation.setConfirmed(true);

        // save reservation in database
        reservationRepository.save(reservation);


    }
}
