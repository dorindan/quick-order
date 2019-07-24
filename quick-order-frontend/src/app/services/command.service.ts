import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './api.service';
import {Command} from '../models/Command';

@Injectable({
  providedIn: 'root'
})
export class CommandService {

  constructor(private http: HttpClient,
              private apiService: ApiService) {
  }

  getAllUnconfirmed(): Observable<Command[]> {
    return this.apiService.getRequest('/api/command/unconfirmed');
  }

  confirmCommand(command: Command): Observable<any> {
    return this.apiService.putRequest('/api/command/confirm', command);
  }

  getCommandsOfUser(): Observable<any> {
    return this.apiService.getRequest('/api/command/actual-user');
  }

  deleteCommand(commandName: string): Observable<any> {
    return this.apiService.deleteRequest('/api/command/remove/' + commandName);
  }
}
