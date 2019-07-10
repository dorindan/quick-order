import {Component} from '@angular/core';
import {TokenStorageService} from './auth/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  private roles: string[];
  private authority: string;

  constructor(private tokenStorage: TokenStorageService) {
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_WAITER') {
          this.authority = 'waiter';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
  }
}
