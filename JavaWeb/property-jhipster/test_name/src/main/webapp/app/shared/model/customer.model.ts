import { IUser } from 'app/shared/model/user.model';
import { IAddress } from 'app/shared/model/address.model';
import { IName } from 'app/shared/model/name.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  email?: string;
  phone?: string;
  birth?: string;
  gender?: Gender;
  user?: IUser;
  address?: IAddress;
  name?: IName;
}

export const defaultValue: Readonly<ICustomer> = {};
