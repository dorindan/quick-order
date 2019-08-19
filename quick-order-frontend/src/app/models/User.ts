import {Attribute} from '@angular/core';

export class User {
  public email: string;
  public userAttributeDto: Attribute;
  public roles: string[];
  private username: string;
  private password: string;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
}
