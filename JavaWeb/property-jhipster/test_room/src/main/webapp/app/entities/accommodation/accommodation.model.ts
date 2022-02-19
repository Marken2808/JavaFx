import { IRoom } from 'app/entities/room/room.model';
import { IProperty } from 'app/entities/property/property.model';
import { AccommodationType } from 'app/entities/enumerations/accommodation-type.model';
import { AccommodationStatus } from 'app/entities/enumerations/accommodation-status.model';

export interface IAccommodation {
  id?: number;
  title?: string;
  type?: AccommodationType;
  status?: AccommodationStatus;
  rooms?: IRoom[] | null;
  property?: IProperty | null;
}

export class Accommodation implements IAccommodation {
  constructor(
    public id?: number,
    public title?: string,
    public type?: AccommodationType,
    public status?: AccommodationStatus,
    public rooms?: IRoom[] | null,
    public property?: IProperty | null
  ) {}
}

export function getAccommodationIdentifier(accommodation: IAccommodation): number | undefined {
  return accommodation.id;
}
