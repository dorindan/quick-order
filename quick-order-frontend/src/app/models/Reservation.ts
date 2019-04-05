export class Reservation {
  public checkInTime: string;
  public checkOutTime: string;
  public numberOfPersons: number;
  public confirmed: boolean;
  public reservationName: string;


  constructor(dateTimeIn: string, dateTimeOut: string, numberOfPersons: number) {
    this.checkInTime = dateTimeIn;
    this.checkOutTime = dateTimeOut;
    this.numberOfPersons = numberOfPersons;
  }
}
