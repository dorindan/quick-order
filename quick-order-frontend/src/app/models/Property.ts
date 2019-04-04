export class Property {

  private _numeRestaurant: string;
  private _startProgramTime: string;
  private _endProgramTime: string;

  get numeRestaurant(): string {
    return this._numeRestaurant;
  }

  set numeRestaurant(value: string) {
    this._numeRestaurant = value;
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


  constructor(numeRestaurant: string, startProgramTime: string, endProgramTime: string) {
    this._numeRestaurant = numeRestaurant;
    this._startProgramTime = startProgramTime;
    this._endProgramTime = endProgramTime;
  }
}
