import { Moment } from 'moment';

export interface IVehicle {
  id?: number;
  brand?: string;
  model?: string;
  vehicleType?: string;
  plateCountry?: string;
  plateNumber?: string;
  vin?: string;
  creationDate?: Moment;
  manufacturedCountry?: string;
}

export class Vehicle implements IVehicle {
  constructor(
    public id?: number,
    public brand?: string,
    public model?: string,
    public vehicleType?: string,
    public plateCountry?: string,
    public plateNumber?: string,
    public vin?: string,
    public creationDate?: Moment,
    public manufacturedCountry?: string
  ) {}
}
