export class Reservation {
  dateTime: string;
  numberOfPersons: number;

  constructor(dateTime : string, nrOfPersons: number) {
    this.dateTime = dateTime;
    this.numberOfPersons = nrOfPersons;
  }
}
