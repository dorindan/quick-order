export class Reservation {
  checkInTime: string;
  checkOutTime: string;
  numberOfPersons: number;
  confirmed: boolean;
  reservationName: string;


  constructor(dateTimeIn: string, dateTimeOut: string, numberOfPersons: number, reservationName: string, confirmed: boolean) {
    this.checkInTime = dateTimeIn;
    this.checkOutTime = dateTimeOut;
    this.numberOfPersons = numberOfPersons;
    this.reservationName = reservationName;
    this.confirmed = confirmed;
  }
}
