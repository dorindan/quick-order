
export class Table {

  public tableNr: number;
  public seats: number;
  public windowView: boolean;
  public floor: number;

  constructor(tableNr: number, seats: number, windowView: boolean, floor: number) {
    this.tableNr = tableNr;
    this.seats = seats;
    this.windowView = windowView;
    this.floor = floor;
  }

}
