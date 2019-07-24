import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {User} from '../models/User';
import {Observable} from 'rxjs';
import {MenuItem} from '../models/MenuItem';
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

  hasActiveCommand(userName: string): Observable<Command> {
    return this.apiService.getRequest('/api/command/hasCommand/' + userName);
  }

  updateCommand(command: Command): Observable<any> {
    return this.apiService.putRequest('/api/command/update/', command);
  }

  addCommand(command: Command): Observable<any> {
    return this.apiService.postRequest('/api/command/add/', command);
  }


}
