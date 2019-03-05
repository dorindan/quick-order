import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiService} from "./api.service";
import {User} from "../models/User";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {Reservation} from "../models/Reservation";
import {RequestOptions} from "@angular/http";
import {catchError} from "rxjs/operators";


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'my-auth-token'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ReservationService{

  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ){}



  // reserve(reservation: Reservation): Observable<Reservation> {
  //   return this.http.post<Reservation>("api/reservation", reservation, httpOptions)
  //     .pipe(
  //       catchError(this.handleError('addReservation', reservation))
  //     );
  // }

  reserve(reservation: Reservation): Observable<any> {
    return this.apiService.postRequest("api/reservation",reservation);
  }

}


