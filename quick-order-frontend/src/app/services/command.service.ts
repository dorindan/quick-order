import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Command} from '../models/Command';

@Injectable({
  providedIn: 'root'
})
export class CommandService {
  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ) {
  }

  updateCommand(command: Command): Observable<any> {
    return this.apiService.putRequest('/api/command/update/', command);
  }

  addCommand(command: Command): Observable<Command> {
    return this.apiService.postRequest('/api/command/add/', command);
  }

}
