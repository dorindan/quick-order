import {Component, OnInit} from '@angular/core';
import {Table} from "../../models/Table";
import {TableService} from "../../services/table.service";
import {Observable} from "rxjs";
import {ReservationService} from "../../services/reservation.service";
import {Reservation} from "../../models/Reservation";


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
  private selectedOptions: any[];

  constructor(private tableService: TableService , private reservationService: ReservationService) {
  }

  ngOnInit() {
    this.tablesGet = this.tableService.getTables();
    this.tables = [];
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));

    this.reservationsGet = this.reservationService.getUnacceptedReservation();
    this.reservations = [];
    this.reservationsGet.forEach(reservation => reservation.forEach(r => this.reservations.push(r)));
    console.log(this.reservations);
  }

  selection(list) {
    this.selectedOptions = list.selectedOptions.selected.map(item => console.log(item.value));
  }

}
