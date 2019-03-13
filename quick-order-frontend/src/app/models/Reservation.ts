export class Reservation {
  checkInTime: string;
  numberOfPersons: number;

  constructor(checkInTime: string, numberOfPersons: number) {
    this.checkInTime = checkInTime;
    this.numberOfPersons = numberOfPersons;
  }
}
