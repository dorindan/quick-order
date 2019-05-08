import {Table} from './Table';

export class ConfirmReservation {
  checkInTime: string;
  numberOfPersons: number;
  checkOutTime: string;
  reservationName: string;
  public tableFoodDtos: Table[];

  constructor(checkInTime: string, numberOfPersons: number, checkOutTime: string, reservationName: string, tableFoodDtos: Table[]) {
    this.checkInTime = checkInTime;
    this.numberOfPersons = numberOfPersons;
    this.checkOutTime = checkOutTime;
    this.reservationName = reservationName;
    this.tableFoodDtos = tableFoodDtos;
  }
}
