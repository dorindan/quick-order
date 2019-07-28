import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Reservation} from '../models/Reservation';
import {ConfirmReservation} from '../models/ConfirmReservation';


@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ) {
  }

  reserve(reservation: Reservation): Observable<any> {
    return this.apiService.postRequest('/api/reservation', reservation);
  }

  getReservation(tableNr: number): Observable<any> {
    return this.apiService.getRequest('/api/reservation/reservationsForTable/' + tableNr);
  }

  getUnacceptedReservation(): Observable<Reservation[]> {
    return this.apiService.getRequest('/api/reservation/unconfirmed');
  }

  confirmReservation(confirmReservation: ConfirmReservation): Observable<any> {
    return this.apiService.putRequest('/api/reservation/confirm', confirmReservation);
  }

  getReservationOfUser(): Observable<any> {
    return this.apiService.getRequest('/api/reservation/actual-user');
  }

  deleteReservation(reservationName: string): Observable<any> {
    return this.apiService.deleteRequest('/api/reservation/remove/' + reservationName);
  }
}
