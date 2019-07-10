import {Table} from './Table';
import {User} from './User';

export class Reservation {
  public checkInTime: string;
  public checkOutTime: string;
  public numberOfPersons: number;
  public confirmed: boolean;
  public reservationName: string;
  public tableFoodDtos: Table[];
  public user: User;

  constructor(dateTimeIn: string, dateTimeOut: string, numberOfPersons: number, reservationName: string, confirmed: boolean, user: User) {
    this.checkInTime = dateTimeIn;
    this.checkOutTime = dateTimeOut;
    this.numberOfPersons = numberOfPersons;
    this.reservationName = reservationName;
    this.confirmed = confirmed;
    this.user = user;
  }
}
