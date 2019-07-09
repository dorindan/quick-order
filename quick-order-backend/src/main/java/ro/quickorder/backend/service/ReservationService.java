package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ro.quickorder.backend.converter.ReservationConverter;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ConfirmReservationDto;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private ReservationConverter reservationConverter;
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
        reservationDto.setStatus("not acccepted");
        reservationDto.setConfirmed(false);
        UUID uuid = UUID.randomUUID();
        reservationDto.setReservationName(uuid.toString().substring(0, 9));
        long twoHoursInMilliseconds = 7200000;
        Timestamp checkOutTime = new Timestamp(reservationDto.getCheckInTime().getTime() + twoHoursInMilliseconds);
        reservationDto.setCheckOutTime(checkOutTime);
        Reservation reservation = reservationConverter.toReservation(reservationDto);
        reservationRepository.save(reservation);
    }

    public List<ReservationDto> getAllUnconfirmed() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().filter(reservation -> !reservation.isConfirmed()).map(reservationConverter::toReservationDto).collect(Collectors.toList());
    }

    public void confirmReservation(ConfirmReservationDto confirmReservationDto) {
        List<TableFoodDto> tableFoodDtos = confirmReservationDto.getTableFoodDtos();
        ReservationDto reservationDto = reservationConverter.toReservationDtoFromConfirmReservationDto(confirmReservationDto);
        if (reservationDto.getReservationName() == null) {
            LOG.error("Reservation not found");
            throw new NotFoundException("Reservation not found");
        }
        // find reservation
        Reservation reservation = getReservationEntityByName(reservationDto.getReservationName());
        // find tables
        List<TableFood> reservationTables = getTablesByName(tableFoodDtos);
        // put tables in reservation
        reservation.setTables(reservationTables);
        reservation.setConfirmed(true);
        // save reservation in database
        reservationRepository.save(reservation);
    }

    public Reservation getReservationEntityByName(String reservationName) {
        // find reservation
        Reservation reservation = reservationRepository.findByReservationName(reservationName);
        if (reservation == null) {
            LOG.error("Reservation not found");
            throw new NotFoundException("Reservation not found");
        }
        return reservation;
    }

    private List<TableFood> getTablesByName(List<TableFoodDto> tableFoodDtos) {
        if (CollectionUtils.isEmpty(tableFoodDtos)) {
            LOG.error("The list of tables can not be empty");
            throw new ForbiddenException("The list of tables can not be empty");
        }
        List<TableFood> tableFoods = new ArrayList<>();
        tableFoodDtos.forEach(tableFoodDto -> {
            TableFood tableFood = tableFoodRepository.findByTableNr(tableFoodDto.getTableNr());
            if (tableFood == null) {
                LOG.error("Table not found");
                throw new NotFoundException("Table not found");
            }
            tableFoods.add(tableFood);
        });
        return tableFoods;
    }

    public List<ReservationDto> getReservationsForTableByTableNumber(Integer tableNr) {
        TableFood tableFood = tableFoodRepository.findByTableNr(tableNr);
        if (tableFood == null) {
            throw new NotFoundException("Table not found!");
        }
        List<Reservation> reservations = reservationRepository.findReservationByTable(tableFood);
        return reservations.stream().map(reservationConverter::toReservationDto).collect(Collectors.toList());
    }
}
