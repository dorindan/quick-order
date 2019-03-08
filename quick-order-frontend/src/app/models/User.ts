import {Attribute} from '@angular/core';

export class User {
  private username: string;
  private password: string;
  public email: string;
  public userAttributeDto: Attribute;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
}
