import { IAddress } from 'app/entities/address/address.model';
import { IRoom } from 'app/entities/room/room.model';
import { PropertyType } from 'app/entities/enumerations/property-type.model';
import { PropertyStatus } from 'app/entities/enumerations/property-status.model';

export interface IProperty {
  id?: number;
  title?: string;
  type?: PropertyType;
  status?: PropertyStatus;
  isUrgent?: boolean | null;
  address?: IAddress;
  rooms?: IRoom[] | null;
}

export class Property implements IProperty {
  constructor(
    public id?: number,
    public title?: string,
    public type?: PropertyType,
    public status?: PropertyStatus,
    public isUrgent?: boolean | null,
    public address?: IAddress,
    public rooms?: IRoom[] | null
  ) {
    this.isUrgent = this.isUrgent ?? false;
  }
}

export function getPropertyIdentifier(property: IProperty): number | undefined {
  return property.id;
}
