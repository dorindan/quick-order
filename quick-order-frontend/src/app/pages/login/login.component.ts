import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {User} from '../../models/User';
import {LoginService} from "../../services/login.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  model: any = {};

  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient,
              private loginService: LoginService) { }

  ngOnInit() {
    sessionStorage.setItem('token', '');
  }

  login() {
      var user = new User(this.model.username,this.model.password)
      this.loginService.login(user).subscribe(isValid => {
        if (isValid) {
          sessionStorage.setItem(
            'token',
            btoa(this.model.username + ':' + this.model.password)
          );
          this.router.navigate(['loggedStart']);
        } else {
          alert('Authentication failed.');
        }
      });
  }

}
