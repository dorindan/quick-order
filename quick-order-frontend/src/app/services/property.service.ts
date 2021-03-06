import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Property} from '../models/Property';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {
  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ) {
  }

  getBistroProperty(): Observable<Property> {
    return this.apiService.getRequest('/api/property/bistro');
  }

  setBistroProperty(property: Property): Observable<any> {
    return this.apiService.postRequest('/api/property/updateProperty', property);
  }

}
