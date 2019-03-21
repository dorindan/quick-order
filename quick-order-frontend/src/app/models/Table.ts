
export class Table {

  private tableNr: number;
  private seats: number;
  private windowView: boolean;
  private floor: number;

  constructor(tableNr: number, seats: number, windowView: boolean, floor: number) {
    this.tableNr = tableNr;
    this.seats = seats;
    this.windowView = windowView;
    this.floor = floor;
  }

}
