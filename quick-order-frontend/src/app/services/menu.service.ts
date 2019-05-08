import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Menu} from '../models/Menu';
import {MenuItem} from '../models/MenuItem';
import {MenuItemType} from '../models/MenuItemType';

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

  getMenuItemType(): Observable<MenuItemType[]> {
    return this.apiService.getRequest('api/menuItemType/all');
  }

  addMenuItemType(menuItemType: MenuItemType): Observable<any> {
    return this.apiService.postRequest('api/menuItemType/add', menuItemType);
  }

  getMenuItems(): Observable<MenuItem[]> {
    return this.apiService.getRequest('api/menuItem/all');
  }

  addMenuItem(menuItem: MenuItem): Observable<any> {
    return this.apiService.postRequest('api/menuItem/add', menuItem);
  }

  editMenuItem(menuItem: MenuItem): Observable<any> {
    return this.apiService.putRequest('api/menuItem/update', menuItem);
  }

  deleteMenuItem(menuItemName: string): Observable<any> {
    return this.apiService.deleteRequest('api/menuItem/remove/' + menuItemName);
  }
}
