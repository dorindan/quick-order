package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.ReservationConverter;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private TableFoodRepository tableFoodRepository;

    public void addReservation(ReservationDto reservationDto) {
        reservationDto.setStatus("not accepted");
        reservationDto.setConfirmed(false);
        long twoHoursInMilliseconds = 7200000;
        Timestamp checkOutTime = new Timestamp(reservationDto.getCheckInTime().getTime() + twoHoursInMilliseconds);
        reservationDto.setCheckOutTime(checkOutTime);
        reservationDto.setReservationName(UUID.randomUUID().toString().substring(0,8));
        Reservation reservation = ReservationConverter.toReservation(reservationDto);
        reservationRepository.save(reservation);
    }



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

        if (reservationDto.getReservationName() == null)
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
        Reservation reservation = reservationRepository.findByReservationName(reservationDto.getReservationName());
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
            TableFood tableFood = tableFoodRepository.findByTableNr(tableFoodDto.getTableNr());
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
