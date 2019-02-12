import {Injectable} from "@angular/core";
import {User} from "../models/User";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LoginService{

  constructor(
    private http: HttpClient,
  ){}



  login(authData: User): Observable<any> {
    return this.http.post('http://localhost:8080/api/login', authData);
  }
}
