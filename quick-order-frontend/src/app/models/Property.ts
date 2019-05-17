export class Property {
  constructor(startProgramTime: string, endProgramTime: string) {
    this._startProgramTime = startProgramTime;
    this._endProgramTime = endProgramTime;
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
