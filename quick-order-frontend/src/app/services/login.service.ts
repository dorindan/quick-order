import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class LoginService{
  set isAuth(value: boolean) {
    this._isAuth = value;
  }

  private _isAuth: boolean = false;

  public constructor(){}


  get isAuth(): boolean {
    return this._isAuth;
  }
}
