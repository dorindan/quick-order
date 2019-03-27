import { Component, OnInit } from '@angular/core';
import {Table} from '../../models/Table';
import {Observable} from 'rxjs';
import {TableService} from '../../services/table.service';
import {Reservation} from '../../models/Reservation';
import {ReservationService} from '../../services/reservation.service';
import {ApiService} from '../../services/api.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.scss']
})
export class TableViewComponent implements OnInit {

  selectedOptions: number[];
  tables: Table[];
  tablesGet: Observable<Table[]>;
  reservations: Reservation[];
  reservationsGet: Observable<Reservation[]>;

  constructor(private tableService: TableService, private reservationService: ReservationService) { }

  ngOnInit() {
    this.tablesGet = this.tableService.getAllTables();
    this.tables = [];
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));
  }

  selection() {
    this.selectedOptions.forEach(item => console.log(item));
  }

  openGroup(i: number) {
    this.reservationsGet = this.reservationService.getReservation(this.tables[i].tableNr);
    this.reservations = [];
    this.reservationsGet.forEach(reservation => reservation.forEach(t => this.reservations.push(t)));
  }

  checkDisabled(i: number): boolean {
    return false;
  }

}
