import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
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

  addTable(table: Table): Observable<any> {
    return this.apiService.postRequest('api/table/add', table);
  }

  editTable(table: Table): Observable<any> {
    return this.apiService.postRequest('api/table/update', table);
  }

  deleteTable(tableNr: number): Observable<any> {
    return this.apiService.deleteRequest('api/table/remove/' + tableNr);
  }

}
