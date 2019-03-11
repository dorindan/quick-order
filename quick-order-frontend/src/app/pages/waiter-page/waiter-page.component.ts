import {Component, OnInit} from '@angular/core';

export interface Tables {
  text: string;
  seats: number;
  free: string;
}

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

  columnsToDisplay = ['User name', 'Seats', 'CheckInTime', 'CheckOutTime'];
  expandedElement: Tables | null;
  tables: Tables[] = [
    {text: 'One', seats: 6, free: 'free'},
    {text: 'Two', seats: 4, free: 'free'},
    {text: 'Three', seats: 2, free: 'free'},
    {text: 'Four', seats: 3, free: 'reserved'},
    {text: 'Five', seats: 8, free: 'free'}
  ];
  reservations: Reservation[] = [
    {userName: 'John Doe', seats: 6, checkInTime: '03/03/2019 10:30', checkOutTime: '03/03/2019 12:30'},
    {userName: 'Bob Dob', seats: 7, checkInTime: '04/02/2019 10:30', checkOutTime: '04/02/2019 12:30'},
    {userName: 'John Doe', seats: 6, checkInTime: '03/03/2019 10:30', checkOutTime: '03/03/2019 12:30'},
    {userName: 'Bob Dob', seats: 7, checkInTime: '04/02/2019 10:30', checkOutTime: '04/02/2019 12:30'}
  ];
  private selectedOptions: any[];


  constructor() {
  }

  ngOnInit() {
  }

  selection(list) {
    this.selectedOptions = list.selectedOptions.selected.map(item => console.log(item.value));
  }
}
