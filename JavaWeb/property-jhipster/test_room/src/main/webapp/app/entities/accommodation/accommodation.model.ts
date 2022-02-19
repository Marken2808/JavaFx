import { IRoom } from 'app/entities/room/room.model';
import { AccommodationType } from 'app/entities/enumerations/accommodation-type.model';
import { AccommodationStatus } from 'app/entities/enumerations/accommodation-status.model';

export interface IAccommodation {
  id?: number;
  title?: string;
  type?: AccommodationType;
  status?: AccommodationStatus;
  acreage?: number;
  imageContentType?: string | null;
  image?: string | null;
  price?: number | null;
  rooms?: IRoom[] | null;
}

export class Accommodation implements IAccommodation {
  constructor(
    public id?: number,
    public title?: string,
    public type?: AccommodationType,
    public status?: AccommodationStatus,
    public acreage?: number,
    public imageContentType?: string | null,
    public image?: string | null,
    public price?: number | null,
    public rooms?: IRoom[] | null
  ) {}
}

export function getAccommodationIdentifier(accommodation: IAccommodation): number | undefined {
  return accommodation.id;
}
