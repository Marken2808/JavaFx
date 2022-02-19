import { IAddress } from 'app/entities/address/address.model';
import { IAccommodation } from 'app/entities/accommodation/accommodation.model';
import { PropertyType } from 'app/entities/enumerations/property-type.model';
import { PropertyStatus } from 'app/entities/enumerations/property-status.model';

export interface IProperty {
  id?: number;
  title?: string;
  type?: PropertyType;
  status?: PropertyStatus;
  isUrgent?: boolean | null;
  address?: IAddress;
  accommodation?: IAccommodation | null;
}

export class Property implements IProperty {
  constructor(
    public id?: number,
    public title?: string,
    public type?: PropertyType,
    public status?: PropertyStatus,
    public isUrgent?: boolean | null,
    public address?: IAddress,
    public accommodation?: IAccommodation | null
  ) {
    this.isUrgent = this.isUrgent ?? false;
  }
}

export function getPropertyIdentifier(property: IProperty): number | undefined {
  return property.id;
}
