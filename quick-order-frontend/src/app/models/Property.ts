export class Property {

  public restaurantName: string;

  public startProgramTime: string;

  public endProgramTime: string;

  public streetName: string;

  public latitude: number;

  public longitude: number;

  public email: string;


  constructor(restaurantName: string, startProgramTime: string, endProgramTime: string, streetName: string, latitude: number, longitude: number, email: string) {
    this.restaurantName = restaurantName;
    this.startProgramTime = startProgramTime;
    this.endProgramTime = endProgramTime;
    this.streetName = streetName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.email = email;
  }

}
