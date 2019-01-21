import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  static isAuthenticated()
  {
    if (sessionStorage.getItem('token') == '')
      return false;
    return true;
  }
}
