import {Injectable} from '@angular/core';
import {User} from '../models/User';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ) {
  }

  login(authData: User): Observable<any> {
    return this.apiService.postRequest('api/users/login', authData);
  }
}
