import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BillService {
  private basePath: String = 'http://localhost:8080/bill';
  private options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
  }

  public getBill(id: number): Observable<any> {
    return this.http.get<any>(this.basePath + '?id=' + id, this.options);
  }
}
