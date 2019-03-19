export class Reservation {
  checkInTime: string;
  numberOfPersons: number;
  checkOutTime: string;
  reservationName: string;

  constructor(checkInTime: string, numberOfPersons: number) {
    this.checkInTime = checkInTime;
    this.numberOfPersons = numberOfPersons;
  }
}
