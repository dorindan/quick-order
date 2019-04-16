import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Menu} from '../models/Menu';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ) {
  }

  getMenu(): Observable<Menu[]> {
    return this.apiService.getRequest('api/menuItemType/all');
  }
}
