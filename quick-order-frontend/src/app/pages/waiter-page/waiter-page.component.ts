import { Component, OnInit } from '@angular/core';

export interface Tile {
  cols: number;
  rows: number;
  text: string;
  seats:number;
  free:string;
}

@Component({
  selector: 'app-waiter-page',
  templateUrl: './waiter-page.component.html',
  styleUrls: ['./waiter-page.component.scss']
})
export class WaiterPageComponent implements OnInit {

  tiles: Tile[] = [
    {text: 'One', cols: 3, rows: 1,seats: 6, free: 'free'},
    {text: 'Two', cols: 1, rows: 2, seats : 4, free: 'free'},
    {text: 'Three', cols: 1, rows: 1, seats: 2, free: 'free'},
    {text: 'Four', cols: 2, rows: 1, seats: 3, free: 'reserved'},
    {text: 'Five', cols: 4, rows: 1, seats: 8, free: 'free'}
  ];

  constructor() { }

  ngOnInit() {
  }

}
