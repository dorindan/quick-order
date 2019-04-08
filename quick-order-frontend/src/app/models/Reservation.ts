export class Reservation {
  public checkInTime: string;
  public checkOutTime: string;
  public numberOfPersons: number;
  public confirmed: boolean;
  public reservationName: string;


  constructor(dateTimeIn: string, dateTimeOut: string, numberOfPersons: number, reservationName: string, confirmed: boolean) {
    this.checkInTime = dateTimeIn;
    this.checkOutTime = dateTimeOut;
    this.numberOfPersons = numberOfPersons;
    this.reservationName = reservationName;
    this.confirmed = confirmed;
  }
}
