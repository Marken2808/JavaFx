import { AccommodationType } from 'app/entities/enumerations/accommodation-type.model';
import { AccommodationStatus } from 'app/entities/enumerations/accommodation-status.model';

export interface IAccommodation {
  id?: number;
  title?: string;
  type?: AccommodationType;
  status?: AccommodationStatus;
}

export class Accommodation implements IAccommodation {
  constructor(public id?: number, public title?: string, public type?: AccommodationType, public status?: AccommodationStatus) {}
}

export function getAccommodationIdentifier(accommodation: IAccommodation): number | undefined {
  return accommodation.id;
}
