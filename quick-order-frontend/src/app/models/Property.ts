export class Property {
  constructor(numeRestaurant: string, startProgramTime: string, endProgramTime: string) {
    this._numeRestaurant = numeRestaurant;
    this._startProgramTime = startProgramTime;
    this._endProgramTime = endProgramTime;
  }

  private _numeRestaurant: string;

  get numeRestaurant(): string {
    return this._numeRestaurant;
  }

  set numeRestaurant(value: string) {
    this._numeRestaurant = value;
  }

  private _startProgramTime: string;

  get startProgramTime(): string {
    return this._startProgramTime;
  }

  set startProgramTime(value: string) {
    this._startProgramTime = value;
  }

  private _endProgramTime: string;

  get endProgramTime(): string {
    return this._endProgramTime;
  }

  set endProgramTime(value: string) {
    this._endProgramTime = value;
  }
}
