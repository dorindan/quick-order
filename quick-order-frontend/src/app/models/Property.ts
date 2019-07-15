export class Property {

  private _restaurantName: string;

  private _startProgramTime: string;

  private _endProgramTime: string;

  private _streetName: string;

  private _latitude: number;

  private _longitude: number;

  private _email: string;


  constructor(restaurantName: string, startProgramTime: string, endProgramTime: string, streetName: string, latitude: number, longitude: number, email: string) {
    this._restaurantName = restaurantName;
    this._startProgramTime = startProgramTime;
    this._endProgramTime = endProgramTime;
    this._streetName = streetName;
    this._latitude = latitude;
    this._longitude = longitude;
    this._email = email;
  }

  get restaurantName(): string {
    return this._restaurantName;
  }

  set restaurantName(value: string) {
    this._restaurantName = value;
  }

  get startProgramTime(): string {
    return this._startProgramTime;
  }

  set startProgramTime(value: string) {
    this._startProgramTime = value;
  }

  get endProgramTime(): string {
    return this._endProgramTime;
  }

  set endProgramTime(value: string) {
    this._endProgramTime = value;
  }

  get streetName(): string {
    return this._streetName;
  }

  set streetName(value: string) {
    this._streetName = value;
  }

  get latitude(): number {
    return this._latitude;
  }

  set latitude(value: number) {
    this._latitude = value;
  }

  get longitude(): number {
    return this._longitude;
  }

  set longitude(value: number) {
    this._longitude = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }
}
