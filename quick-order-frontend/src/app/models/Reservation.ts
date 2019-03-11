export class Reservation {
  checkInTime: string;
  numberOfPersons: number;

  constructor(dateTime: string, numberOfPersons: number) {
    this.checkInTime = dateTime;
    this.numberOfPersons = numberOfPersons;
  }
}
