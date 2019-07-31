import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/User';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient,
              private apiService: ApiService) {
  }

  getAllUsers(): Observable<User[]> {
    return this.apiService.getRequest('/api/users/all');
  }

  updateUser(user: User): Observable<any> {
    return this.apiService.putRequest('/api/users/update', user);
  }
}

