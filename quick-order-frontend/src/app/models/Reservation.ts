export class Reservation {
  checkInTime: string;
  numberOfPersons: number;

  constructor(dateTime : string, nrOfPersons: number) {
    this.checkInTime = dateTime;
    this.numberOfPersons = nrOfPersons;
  }
}
