export class Table {
  tableNr: number;
  seats: number;
  windowView: boolean;
  floor: number;
  free: boolean;


  constructor(tableNr: number, seats: number, windowView: boolean, floor: number, free: boolean) {
    this.tableNr = tableNr;
    this.seats = seats;
    this.windowView = windowView;
    this.floor = floor;
    this.free = free;
  }
}
