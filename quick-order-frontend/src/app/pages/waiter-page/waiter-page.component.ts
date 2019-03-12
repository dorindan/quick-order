import {Component, OnInit} from '@angular/core';
import {Table} from "../../models/Table";
import {TableService} from "../../services/table.service";
import {Observable} from "rxjs";

export interface Reservation {
  userName: string;
  seats: number;
  checkInTime: string;
  checkOutTime: string;
}


@Component({
  selector: 'app-waiter-page',
  templateUrl: './waiter-page.component.html',
  styleUrls: ['./waiter-page.component.scss']
})
export class WaiterPageComponent implements OnInit {
  tablesGet: Observable<Table[]>;
  tables: Table[];

  reservations: Reservation[] = [
    {userName: 'John Doe', seats: 6, checkInTime: '03/03/2019 10:30', checkOutTime: '03/03/2019 12:30'},
    {userName: 'Bob Dob', seats: 7, checkInTime: '04/02/2019 10:30', checkOutTime: '04/02/2019 12:30'},
    {userName: 'John Doe', seats: 6, checkInTime: '03/03/2019 10:30', checkOutTime: '03/03/2019 12:30'},
    {userName: 'Bob Dob', seats: 7, checkInTime: '04/02/2019 10:30', checkOutTime: '04/02/2019 12:30'}
  ];
  private selectedOptions: any[];


  constructor(private tableService: TableService ) {
  }

  ngOnInit() {
    this.tablesGet = this.tableService.getTables();
    this.tables = [];
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));
    console.log(this.tables);
  }

  selection(list) {
    this.selectedOptions = list.selectedOptions.selected.map(item => console.log(item.value));
  }

}
