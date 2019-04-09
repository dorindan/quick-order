import {Reservation} from './Reservation';
import {Table} from './Table';

export class ConfirmReservation {
  public reservation: Reservation;
  public tables: Table[];

  constructor(reservation: Reservation, tables: Table[]) {
    this.reservation = reservation;
    this.tables = tables;
  }
}
