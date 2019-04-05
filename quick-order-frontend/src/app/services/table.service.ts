import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Reservation} from '../models/Reservation';
import {Table} from '../models/Table';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'my-auth-token'
  })
};

@Injectable({
  providedIn: 'root'
})
export class TableService {
  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ) {
  }

  getAllTables(): Observable<Table[]> {
    return this.apiService.getRequest('api/table/all');
  }
}

