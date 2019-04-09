import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Table} from '../../models/Table';
import {TableService} from '../../services/table.service';
import {Observable} from 'rxjs';
import {ReservationService} from '../../services/reservation.service';
import {Reservation} from '../../models/Reservation';
import {ConfirmReservation} from '../../models/ConfirmReservation';


@Component({
  selector: 'app-waiter-page',
  templateUrl: './waiter-page.component.html',
  styleUrls: ['./waiter-page.component.scss']
})
export class WaiterPageComponent implements OnInit {
  tablesGet: Observable<Table[]>;
  tables: Table[];
  reservationsGet: Observable<Reservation[]>;
  reservations: Reservation[];
  selectedOptions: Table[];
  indexExpanded: number;
  disabledElements: number[];
  totalOfSelectedSeats: number;
  i: number;
  j: number;

  constructor(private tableService: TableService, private reservationService: ReservationService) {
  }

  ngOnInit() {
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
    if (this.selectedOptions.length === 0) {
      this.indexExpanded = -1;
    } else {
      this.indexExpanded = -1;
      this.disabledElements.push(index);
    }
    console.log(reservation);
    this.reservationService.confirmReservation(new ConfirmReservation(reservation, this.selectedOptions));
  }

  hint(reservation: Reservation, index: number): String {
    this.totalOfSelectedSeats = 0;
    this.i = 0;
    this.j = 0;
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
    console.log(any);
    this.selectedOptions = [];
    this.indexExpanded = index;
    let checkInTimeFormatted = '';
    checkInTimeFormatted = reservation.checkInTime.substr(0, 10).replace('/', '+').replace('/', '+')
      + '+' + reservation.checkInTime.substr(11, 5);
    let checkOutTimeFormatted = ' ';
    checkOutTimeFormatted = reservation.checkOutTime.substr(0, 10).replace('/', '+').replace('/', '+') + '+' +
      reservation.checkOutTime.substr(11, 5);
    this.tablesGet = this.tableService.getTables(checkInTimeFormatted, checkOutTimeFormatted);
    this.tables = [];
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));
  }

  checkDisabled(i: number): boolean {
    if (this.disabledElements.includes(i)) {
      return true;
    } else {
      return false;
    }
  }

  enableEdit(i: number) {
    this.disabledElements = this.disabledElements.filter(item => item !== i);
  }
}

