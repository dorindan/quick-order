package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ro.quickorder.backend.converter.ReservationConverter;
import ro.quickorder.backend.converter.TableFoodConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ConfirmReservationDto;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;
import ro.quickorder.backend.repository.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationConverter reservationConverter;
    @Autowired
    private TableFoodRepository tableFoodRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TableFoodService tableFoodService;
    @Autowired
    private TableFoodConverter tableFoodConverter;
    @Autowired
    private EmailService emailService;

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
        // set checkOut and checkIn
        long twoHoursInMilliseconds = 7200000;
        Timestamp checkOutTime = new Timestamp(reservationDto.getCheckInTime().getTime() + twoHoursInMilliseconds);
        reservationDto.setCheckOutTime(checkOutTime);
        // set confirmed
        reservationDto.setConfirmed(false);
        // set reservation name
        UUID uuid = UUID.randomUUID();
        reservationDto.setReservationName(uuid.toString().substring(0, 9));
        Reservation reservation = reservationConverter.toReservation(reservationDto);
        List<TableFood> reservations = reservation.getTables();
        reservation.setTables(null);
        if (reservationDto.getUser() != null) {
            reservation.setUser(userRepository.findByUsername(reservationDto.getUser().getUsername()));
        }
        // save reservation in database
        reservation = reservationRepository.save(reservation);

        // treat the reservation as confirmed or unconfirmed
        if (reservations != null && reservations.size() > 0) {
            List<TableFoodDto> tableFoodDtos = reservations.stream()
                    .map(tableFood -> tableFoodConverter.toTableFoodDto(tableFood)).collect(Collectors.toList());
            // find tables and make sure that they are free
            String checkIn = reservationDto.getCheckInTime().toString();
            checkIn = checkIn.substring(8, 10) + "+" + checkIn.substring(5, 7) + "+" + checkIn.substring(0, 4) + "+" + checkIn.substring(checkIn.indexOf(' ') + 1, checkIn.indexOf(':') + 3);
            String checkOut = reservationDto.getCheckOutTime().toString();
            checkOut = checkOut.substring(8, 10) + "+" + checkOut.substring(5, 7) + "+" + checkOut.substring(0, 4) + "+" + checkOut.substring(checkOut.indexOf(' ') + 1, checkOut.indexOf(':') + 3);
            reservation.setTables(getTablesByName(tableFoodDtos, checkIn, checkOut));
            // see if the seats are enough for the number of persons
            personsFitInSeats(reservation);
            reservation.setStatus("acccepted");
            reservation.setConfirmed(true);
        }

        // save reservation in database
        reservationRepository.save(reservation);

        emailService.sendReservationMail(reservation.getNumberOfPersons(), reservation.getCheckInTime(),
                reservation.getCheckOutTime(), reservation.getUser(), false);
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

        if (reservation.isConfirmed()) {
            reservation.setConfirmed(false);
            reservation.setTables(null);
            reservationRepository.save(reservation);
        }

        // find tables
        String checkIn = reservationDto.getCheckInTime().toString();
        checkIn = checkIn.substring(8, 10) + "+" + checkIn.substring(5, 7) + "+" + checkIn.substring(0, 4) + "+" + checkIn.substring(checkIn.indexOf(' ') + 1, checkIn.indexOf(':') + 3);

        String checkOut = reservationDto.getCheckOutTime().toString();
        checkOut = checkOut.substring(8, 10) + "+" + checkOut.substring(5, 7) + "+" + checkOut.substring(0, 4) + "+" + checkOut.substring(checkOut.indexOf(' ') + 1, checkOut.indexOf(':') + 3);
        List<TableFood> reservationTables = getTablesByName(tableFoodDtos, checkIn, checkOut);
        // put tables in reservation
        reservation.setTables(reservationTables);
        // see if the seats are enough for the number of persons
        personsFitInSeats(reservation);
        reservation.setConfirmed(true);
        // save reservation in database
        reservationRepository.save(reservation);

        emailService.sendReservationMail(reservation.getNumberOfPersons(), reservation.getCheckInTime(),
                reservation.getCheckOutTime(), reservation.getUser(), true);
    }

    private Reservation getReservationEntityByName(String reservationName) {
        // find reservation
        Reservation reservation = reservationRepository.findByReservationName(reservationName);
        if (reservation == null) {
            LOG.error("Reservation not found");
            throw new NotFoundException("Reservation not found");
        }
        return reservation;
    }

    public List<ReservationDto> getAllReservations() {
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservationRepository.findAll()) {
            reservationDtos.add(reservationConverter.toReservationDto(reservation));
        }
        return reservationDtos;
    }

    public ReservationDto getReservationDtoByName(String reservationName) {

        Reservation reservation = reservationRepository.findByReservationNameWithTables(reservationName);
        if (reservation == null) {
            LOG.error("Reservation not found");
            throw new NotFoundException("Reservation not found");
        }
        return reservationConverter.toReservationDto(reservation);
    }

    private List<TableFood> getTablesByName(List<TableFoodDto> tableFoodDtos, String checkIn, String checkOut) {
        if (CollectionUtils.isEmpty(tableFoodDtos)) {
            LOG.error("The list of tables can not be empty");
            throw new ForbiddenException("The list of tables can not be empty");
        }
        List<TableFoodDto> freeTables = tableFoodService.getAllFree(checkIn, checkOut);

        List<TableFood> tableFoods = new ArrayList<>();
        tableFoodDtos.forEach(tableFoodDto -> {
            TableFood tableFood = tableFoodRepository.findByTableNr(tableFoodDto.getTableNr());
            if (tableFood == null) {
                LOG.error("Table not found");
                throw new NotFoundException("Table not found");
            }
            if (!freeTables.stream().anyMatch(table -> table.getTableNr() == (tableFoodDto.getTableNr()))) {
                LOG.error("Table not free");
                throw new NotFoundException("Table not free");
            }
            tableFoods.add(tableFood);
        });
        return tableFoods;
    }

    private void personsFitInSeats(Reservation reservation) {
        int nrSeats = reservation.getTables().stream().map(tableFood -> tableFood.getSeats()).reduce(0, (a, b) -> a + b);
        boolean fits = nrSeats < reservation.getNumberOfPersons();
        if (fits) {
            LOG.error("Not enough seats for all persons!");
            throw new BadRequestException("Not enough seats for all persons!");
        }
    }

    public boolean reservationConfirmed(String reservationName) {
        return reservationRepository.findByReservationName(reservationName).isConfirmed();
    }

    public List<ReservationDto> getReservationsForTableByTableNumber(Integer tableNr) {
        TableFood tableFood = tableFoodRepository.findByTableNr(tableNr);
        if (tableFood == null) {
            LOG.error("Table not found!");
            throw new NotFoundException("Table not found!");
        }
        List<Reservation> reservations = reservationRepository.findReservationByTable(tableFood);
        return reservations.stream().map(reservationConverter::toReservationDto).collect(Collectors.toList());
    }

    public List<ReservationDto> reservationOfActualUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        if (username == null) {
            LOG.info("User not authenticated");
            return null;
        } else {
            LOG.info("Reservations of user: " + username + " requested.");

            final List<Reservation> reservations = reservationRepository.findReservationsByUsername(username);

            return reservations.stream()
                    .map(reservationConverter::toReservationDto)
                    .collect(Collectors.toList());

        }
    }

    public void removeReservation(String reservationName) {
        final Reservation reservation = reservationRepository.findByReservationName(reservationName);
        if (reservation == null) {
            LOG.error("Reservation with name: " + reservationName + " was not found!");
            throw new NotFoundException("Reservation not found!");
        }
        reservationRepository.delete(reservation);
    }
}
