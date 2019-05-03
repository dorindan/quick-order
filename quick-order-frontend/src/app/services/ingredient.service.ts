import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Ingredient} from '../models/Ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ) {
  }

  getIngredient(): Observable<Ingredient[]> {
    return this.apiService.getRequest('api/ingredient/all');
  }

  addIngredient(ingredient: Ingredient): Observable<any> {
    return this.apiService.postRequest('api/ingredient/add', ingredient);
  }

}
