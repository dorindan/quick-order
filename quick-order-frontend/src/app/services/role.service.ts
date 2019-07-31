import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Authorities} from '../models/Authorities';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  constructor(private http: HttpClient,
              private apiService: ApiService) {
  }

  getAllRoles(): Observable<Authorities> {
    return this.apiService.getRequest('/api/role/all');
  }
}
