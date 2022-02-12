import { IAddress } from 'app/shared/model/address.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { Type } from 'app/shared/model/enumerations/type.model';

export interface IProperty {
  id?: number;
  title?: string;
  imageContentType?: string;
  image?: string;
  status?: Status;
  type?: Type;
  acreage?: number;
  address?: IAddress;
}

export const defaultValue: Readonly<IProperty> = {};
