import {Component, OnInit} from '@angular/core';
import {Table} from '../../models/Table';
import {Observable} from 'rxjs';
import {TableService} from '../../services/table.service';
import {Reservation} from '../../models/Reservation';
import {ReservationService} from '../../services/reservation.service';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.scss']
})
export class TableViewComponent implements OnInit {
  tables: Table[];
  tablesGet: Observable<Table[]>;
  reservations: Reservation[];
  reservationsGet: Observable<Reservation[]>;

  constructor(private tableService: TableService, private reservationService: ReservationService) {
  }

  ngOnInit() {
    this.tablesGet = this.tableService.getAllTables();
    this.tables = [];
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));
  }

  openGroup(i: number) {
    this.reservationsGet = this.reservationService.getReservation(this.tables[i].tableNr);
    this.reservations = [];
    this.reservationsGet.forEach(reservation => reservation.forEach(t => this.reservations.push(t)));
  }

  getFormattedTimeIn(reservation: Reservation): string {
    return reservation.checkInTime.substr(0, 10) + ' ' +
      reservation.checkInTime.substr(11, 5);
  }

  getFormattedTimeOut(reservation: Reservation): string {
    return reservation.checkOutTime.substr(0, 10) + ' ' +
      reservation.checkOutTime.substr(11, 5);
  }
}
