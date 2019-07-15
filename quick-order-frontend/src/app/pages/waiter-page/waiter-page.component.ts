import {Component, OnInit} from '@angular/core';
import {Table} from '../../models/Table';
import {TableService} from '../../services/table.service';
import {Observable} from 'rxjs';
import {ReservationService} from '../../services/reservation.service';
import {Reservation} from '../../models/Reservation';
import {ConfirmReservation} from '../../models/ConfirmReservation';
import {MatSnackBar} from '@angular/material';
import {TokenStorageService} from '../../auth/token-storage.service';


@Component({
  selector: 'app-waiter-page',
  templateUrl: './waiter-page.component.html',
  styleUrls: ['./waiter-page.component.scss']
})
export class WaiterPageComponent implements OnInit {
  tablesGet: Observable<Table[]>;
  tables: Table[];
  tablesAssignedGet: Observable<Table[]>;
  reservationsGet: Observable<Reservation[]>;
  reservations: Reservation[];
  selectedOptions: Table[];
  indexExpanded: number;
  disabledElements: number[];
  totalOfSelectedSeats: number;
  private info: any;
  // contains the index if the obj is confirmed
  private actualConfirmed = -1;

  constructor(private tableService: TableService, private reservationService: ReservationService
    , private snackBar: MatSnackBar, private token: TokenStorageService) {
  }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };
    this.reservationsGet = this.reservationService.getUnacceptedReservation();
    this.reservations = [];
    this.reservationsGet.forEach(reservation => reservation.forEach(r => this.reservations.push(r)));
    this.indexExpanded = -1;
    this.disabledElements = [];
    this.selectedOptions = [];
  }

  selection() {
    this.selectedOptions.forEach(table => console.log(table));
  }

  getFormattedTime(reservation: Reservation): string {
    return reservation.checkInTime.substr(0, 10) + ' ' +
      reservation.checkInTime.substr(11, 5) + ' - ' +
      reservation.checkOutTime.substr(0, 10) + ' ' +
      reservation.checkOutTime.substr(11, 5);
  }

  acceptReservation(reservation: Reservation, index: number) {
    if (this.hint(reservation) === '') {

      if (this.selectedOptions.length === 0) {
        this.indexExpanded = -1;
      } else {
        this.indexExpanded = -1;
        this.disabledElements.push(index);
      }
      this.reservationService.confirmReservation(new ConfirmReservation(reservation.checkInTime,
        reservation.numberOfPersons, reservation.checkOutTime, reservation.reservationName, this.selectedOptions))
        .subscribe(data => {
          this.showSnackbar('Reservation confirmed successfully.');
        }, error => {
          switch (error.status) {
            case 404: // not found exception
              this.showSnackbar('Reservation not found. Please try again.');
              break;
            default:
              this.showSnackbar('Confirmation failed. Please try again.');
          }
        });

    }
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      panelClass: ['snackbar']
    });
  }

  hint(reservation: Reservation): String {
    this.totalOfSelectedSeats = 0;
    for (const option of this.selectedOptions) {
      for (const table of this.tables) {
        if (table.tableNr === option.tableNr) {
          this.totalOfSelectedSeats = this.totalOfSelectedSeats + table.seats;
        }
      }
    }
    if (reservation.numberOfPersons > this.totalOfSelectedSeats) {
      return 'You selected tables with less seats than requested.';
    }
    return '';
  }

  openGroup(any, index: number, reservation: Reservation) {
    this.selectedOptions = [];
    this.indexExpanded = index;
    let checkInTimeFormatted;
    checkInTimeFormatted = reservation.checkInTime.substr(0, 10).replace('/', '+').replace('/', '+')
      + '+' + reservation.checkInTime.substr(11, 5);
    let checkOutTimeFormatted;
    checkOutTimeFormatted = reservation.checkOutTime.substr(0, 10).replace('/', '+').replace('/', '+') + '+' +
      reservation.checkOutTime.substr(11, 5);

    // get all tables that are reserved by this reservation, if it is actualConfirmed
    this.tables = [];
    this.actualConfirmed = -1;
    this.tablesAssignedGet = this.tableService.getAllAssignedTablesOfAReservation(reservation.reservationName);
    this.tablesAssignedGet.forEach(table => table.forEach(t => {
      this.tables.push(t);
      if (this.actualConfirmed === -1) {
        this.actualConfirmed = index;
      }
    }));

    // get all free tables
    this.tablesGet = this.tableService.getTables(checkInTimeFormatted, checkOutTimeFormatted);
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));

  }

  checkDisabled(i: number): boolean {
    return this.disabledElements.includes(i);
  }

  enableEdit(i: number) {
    this.disabledElements = this.disabledElements.filter(item => item !== i);
  }

  closeGroup(): void {
    if (this.actualConfirmed !== -1) {
      this.disabledElements.push(this.actualConfirmed);
    }
  }

}

