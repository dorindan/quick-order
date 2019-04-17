import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Menu} from '../models/Menu';
import {MenuItem} from '../models/MenuItem';

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

  getMenuItems(): Observable<MenuItem[]> {
    return this.apiService.getRequest('api/menuItem/all');
  }

  addMenuItem(menuItem: MenuItem): void {
    this.apiService.postRequest('api/menuItem/add', menuItem);
  }

  editMenuItem(menuItem: MenuItem): void {
    this.apiService.postRequest('api/menuItem/update', menuItem);
  }

  deleteMenuItem(menuItemName: string): void {
    this.apiService.deleteRequest('api/menuItem/remove/' + menuItemName);
  }
}
