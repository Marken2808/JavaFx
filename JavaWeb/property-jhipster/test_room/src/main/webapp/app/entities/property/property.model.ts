import { IAddress } from 'app/entities/address/address.model';
import { IAccommodation } from 'app/entities/accommodation/accommodation.model';
import { IProject } from 'app/entities/project/project.model';
import { ILand } from 'app/entities/land/land.model';
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
  accommodation?: IAccommodation | null;
  project?: IProject | null;
  land?: ILand | null;
  customers?: ICustomer[];
}

export class Property implements IProperty {
  constructor(
    public id?: number,
    public title?: string,
    public type?: PropertyType,
    public status?: PropertyStatus,
    public isUrgent?: boolean | null,
    public address?: IAddress,
    public accommodation?: IAccommodation | null,
    public project?: IProject | null,
    public land?: ILand | null,
    public customers?: ICustomer[]
  ) {
    this.isUrgent = this.isUrgent ?? false;
  }
}

export function getPropertyIdentifier(property: IProperty): number | undefined {
  return property.id;
}
