import { IAddress } from 'app/entities/address/address.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { PropertyType } from 'app/entities/enumerations/property-type.model';
import { PropertyStatus } from 'app/entities/enumerations/property-status.model';

export interface IProperty {
  id?: number;
  title?: string;
  type?: PropertyType;
  status?: PropertyStatus;
  isUrgent?: boolean | null;
  address?: IAddress;
  customers?: ICustomer[] | null;
}

export class Property implements IProperty {
  constructor(
    public id?: number,
    public title?: string,
    public type?: PropertyType,
    public status?: PropertyStatus,
    public isUrgent?: boolean | null,
    public address?: IAddress,
    public customers?: ICustomer[] | null
  ) {
    this.isUrgent = this.isUrgent ?? false;
  }
}

export function getPropertyIdentifier(property: IProperty): number | undefined {
  return property.id;
}
