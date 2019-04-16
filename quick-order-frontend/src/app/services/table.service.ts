import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Table} from '../models/Table';

@Injectable({
  providedIn: 'root'
})
export class TableService {
  constructor(private http: HttpClient,
              private apiService: ApiService) {
  }

  getTables(checkInTime: string, checkOutTime: string): Observable<Table[]> {
    return this.apiService.getRequest('api/table/free/' + checkInTime + '/' + checkOutTime);
  }
}
