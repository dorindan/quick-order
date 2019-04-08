package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    private ReservationConverter reservationConverter;


    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private TableFoodRepository tableFoodRepository;

    public void addReservation(ReservationDto reservationDto) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if ((reservationDto.getNumberOfPersons() >= 100 || reservationDto.getNumberOfPersons() < 1)) {
            LOG.error("Number of persons for a reservation must be between 1 and 99");
            throw new ForbiddenException("Number of persons for a reservation must be between 1 and 99");
        }
        if (reservationDto.getCheckInTime().before(currentTimestamp)) {
            LOG.error("CheckInTime must be greater than the current date currentTimeStamp: " + currentTimestamp + " givenTimeStamp: " + reservationDto.getCheckInTime());
            throw new ForbiddenException("CheckInTime must be greater than the current date");
        }
        LOG.info(currentTimestamp + " givenTimeStamp: " + reservationDto.getCheckInTime());
        reservationDto.setStatus("not accepted");
        reservationDto.setConfirmed(false);
        long twoHoursInMilliseconds = 7200000;
        Timestamp checkOutTime = new Timestamp(reservationDto.getCheckInTime().getTime() + twoHoursInMilliseconds);
        reservationDto.setCheckOutTime(checkOutTime);
        Reservation reservation = reservationConverter.toReservation(reservationDto);
        reservation.setReservationName(generateRandomName());
        reservationRepository.save(reservation);
    }

    public List<ReservationDto> getAllUnconfirmed() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDto> results = new ArrayList<>();

        for (Reservation res : reservations) {
            if (!res.isConfirmed()) {
                results.add(reservationConverter.toReservationDto(res));
            }
        }
        return results;
    }

    public void confirmReservation(ReservationDto reservationDto, List<TableFoodDto> tableFoodDtos) {

        if (reservationDto.getReservationName() == null) {
            LOG.error("Reservation not found");
            throw new NotFoundException("Reservation not found");
        }

        // find reservation
        Reservation reservation = getReservationEntityByName(reservationDto);

        // find tables
        List<TableFood> reservationTables = getTablesByName(tableFoodDtos);

        // put tables in reservation
        reservation.setTables(reservationTables);
        reservation.setConfirmed(true);

        // save reservation in database
        reservationRepository.save(reservation);

    }

    private Reservation getReservationEntityByName(ReservationDto reservationDto) {
        // find reservation
        Reservation reservation = reservationRepository.findByReservationName(reservationDto.getReservationName());
        if (reservation.isConfirmed()) {
            LOG.error("Reservation is already confirmed");
            throw new NotFoundException("Reservation is already confirmed");
        }
        if (reservation == null) {
            LOG.error("Reservation not found");
            throw new NotFoundException("Reservation not found");
        }
        return reservation;
    }

    private List<TableFood> getTablesByName(List<TableFoodDto> tableFoodDtos) {
        if (tableFoodDtos.size() == 0) {
            LOG.error("TableList can not be null");
            throw new ForbiddenException("TableList can not be null");
        }
        List<TableFood> tableFoodListToSet = new ArrayList<>();
        for (TableFoodDto tableFoodDto : tableFoodDtos) {
            TableFood tableFood = tableFoodRepository.findByTableNr(tableFoodDto.getTableNr());
            if (tableFood == null) {
                LOG.error("Table not found");
                throw new NotFoundException("Table not found");
            }
            tableFoodListToSet.add(tableFood);
        }
        return tableFoodListToSet;
    }

    public List<ReservationDto> getReservationsForTableByTableNumber (Integer tableNr){
        List<ReservationDto> res = new ArrayList<>();
        TableFood tableFood = tableFoodRepository.findByTableNr(tableNr);
        if (tableFood == null) {
            throw new NotFoundException("Table not found!");
        }
        List<Reservation> reservations = reservationRepository.findReservationByTable(tableFood);

        for (Reservation reservation : reservations) {
            res.add(reservationConverter.toReservationDto(reservation));
        }
        return res;
    }

    public void addReservationConfirmed(ReservationDto reservationDto) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if ((reservationDto.getNumberOfPersons() >= 100 || reservationDto.getNumberOfPersons() < 1)) {
            LOG.error("Number of persons for a reservation must be between 1 and 99");
            throw new ForbiddenException("Number of persons for a reservation must be between 1 and 99");
        }
        if (reservationDto.getCheckInTime().before(currentTimestamp)) {
            LOG.error("CheckInTime must be greater than the current date currentTimeStamp: " + currentTimestamp + " givenTimeStamp: " + reservationDto.getCheckInTime());
            throw new ForbiddenException("CheckInTime must be greater than the current date");
        }
        reservationDto.setStatus("accepted");
        reservationDto.setConfirmed(true);
        long twoHoursInMilliseconds = 7200000;
        Timestamp checkOutTime = new Timestamp(reservationDto.getCheckInTime().getTime() + twoHoursInMilliseconds);
        reservationDto.setCheckOutTime(checkOutTime);
        Reservation reservation = reservationConverter.toReservation(reservationDto);
        reservation.setReservationName(generateRandomName());
        reservationRepository.save(reservation);
    }

    private String generateRandomName() {
        String name;
        Reservation reservation;
        do {
            name = UUID.randomUUID().toString().substring(0, 8);
            reservation = reservationRepository.findByReservationName(name);
        } while (reservation != null);
        return name;
    }
}
