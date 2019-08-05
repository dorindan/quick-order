import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/User';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/api/test/user';
  private pmUrl = 'http://localhost:8080/api/test/pm';
  private adminUrl = 'http://localhost:8080/api/test/admin';

  constructor(private http: HttpClient,
              private apiService: ApiService) {
  }

  getUserBoard(): Observable<string> {
    return this.http.get(this.userUrl, {responseType: 'text'});
  }

  getAllUsers(): Observable<User[]> {
    return this.apiService.getRequest('/api/users/all');
  }

  updateUser(user: User): Observable<any> {
    return this.apiService.putRequest('/api/users/update', user);
  }
}

