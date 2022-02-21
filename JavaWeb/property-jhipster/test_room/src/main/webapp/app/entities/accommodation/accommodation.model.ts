import { IProperty } from 'app/entities/property/property.model';
import { AccommodationType } from 'app/entities/enumerations/accommodation-type.model';
import { AccommodationStatus } from 'app/entities/enumerations/accommodation-status.model';

export interface IAccommodation {
  id?: number;
  title?: string;
  type?: AccommodationType;
  status?: AccommodationStatus;
  imageContentType?: string | null;
  image?: string | null;
  total?: number | null;
  property?: IProperty | null;
}

export class Accommodation implements IAccommodation {
  constructor(
    public id?: number,
    public title?: string,
    public type?: AccommodationType,
    public status?: AccommodationStatus,
    public imageContentType?: string | null,
    public image?: string | null,
    public total?: number | null,
    public property?: IProperty | null
  ) {}
}

export function getAccommodationIdentifier(accommodation: IAccommodation): number | undefined {
  return accommodation.id;
}
