import {HttpClient} from "@angular/common/http";
import {ApiService} from "./api.service";
import {User} from "../models/User";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {Reservation} from "../models/Reservation";

@Injectable({
  providedIn: 'root'
})
export class ReservationService{

  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ){}



  reserve(reservation: Reservation): Observable<any> {
    return this.apiService.postRequest('api/reservation', reservation);
  }
}


