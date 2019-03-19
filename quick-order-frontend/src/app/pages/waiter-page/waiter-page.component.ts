import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Table} from '../../models/Table';
import {TableService} from '../../services/table.service';
import {Observable} from 'rxjs';
import {ReservationService} from '../../services/reservation.service';
import {Reservation} from '../../models/Reservation';



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
  selectedOptions: number[];
  indexExpanded: number;
  disabledElements: number[];
  @ViewChild('myExpansionPanel') myExpansionPanel: ElementRef;

  constructor(private tableService: TableService, private reservationService: ReservationService) {
  }

  ngOnInit() {
    this.tablesGet = this.tableService.getTables();
    this.tables = [];
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));

    this.reservationsGet = this.reservationService.getUnacceptedReservation();
    this.reservations = [];
    this.reservationsGet.forEach(reservation => reservation.forEach(r => this.reservations.push(r)));
    console.log(this.reservations);
    this.indexExpanded = -1;
    this.disabledElements = [];
  }

  selection() {
    this.selectedOptions.forEach(item => console.log(item));
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
    console.log(this.indexExpanded);
    console.log(reservation.reservationName + ' ' + this.selectedOptions);
  }

  hint(index: number): String {
    return '';
  }

  openGroup(any, index: number) {
    console.log(any);
    this.selectedOptions = [];
    this.indexExpanded = index;
  }

  checkDisabled(i: number): boolean {
    if (this.disabledElements.includes(i)) {
      return true;
    } else {
      return false;
    }
  }
}

