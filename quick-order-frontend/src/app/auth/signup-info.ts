export class SignUpInfo {
  username: string;
  email: string;
  roles: string[];
  password: string;

  constructor(username: string, email: string, password: string) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = ['user'];
  }
}
