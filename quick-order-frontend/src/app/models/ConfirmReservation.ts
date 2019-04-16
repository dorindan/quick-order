import {Table} from './Table';

export class ConfirmReservation {
  checkInTime: string;
  numberOfPersons: number;
  checkOutTime: string;
  reservationName: string;
  public tableFoodListDto: Table[];


  constructor(checkInTime: string, numberOfPersons: number, checkOutTime: string, reservationName: string, tablesFoodListDto: Table[]) {
    this.checkInTime = checkInTime;
    this.numberOfPersons = numberOfPersons;
    this.checkOutTime = checkOutTime;
    this.reservationName = reservationName;
    this.tableFoodListDto = tablesFoodListDto;
  }
}
