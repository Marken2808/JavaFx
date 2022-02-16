import { IAddress } from 'app/shared/model/address.model';
import { IAccommodation } from 'app/shared/model/accommodation.model';
import { PropertyType } from 'app/shared/model/enumerations/property-type.model';
import { PropertyStatus } from 'app/shared/model/enumerations/property-status.model';

export interface IProperty {
  id?: number;
  title?: string;
  type?: PropertyType;
  status?: PropertyStatus;
  isUrgent?: boolean | null;
  address?: IAddress;
  accommodation?: IAccommodation;
}

export const defaultValue: Readonly<IProperty> = {
  isUrgent: false,
};
