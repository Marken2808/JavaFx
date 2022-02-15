import { IAddress } from 'app/shared/model/address.model';
import { IAccommodation } from 'app/shared/model/accommodation.model';
import { PropertyStatus } from 'app/shared/model/enumerations/property-status.model';
import { PropertyType } from 'app/shared/model/enumerations/property-type.model';

export interface IProperty {
  id?: number;
  title?: string;
  imageContentType?: string;
  image?: string;
  status?: PropertyStatus;
  type?: PropertyType;
  acreage?: number;
  price?: number;
  address?: IAddress;
  accommodation?: IAccommodation;
}

export const defaultValue: Readonly<IProperty> = {};
