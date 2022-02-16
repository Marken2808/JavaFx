import { IUser } from 'app/shared/model/user.model';
import { IName } from 'app/shared/model/name.model';
import { IAddress } from 'app/shared/model/address.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  email?: string;
  phone?: string;
  birth?: string;
  gender?: Gender | null;
  user?: IUser;
  name?: IName;
  address?: IAddress;
}

export const defaultValue: Readonly<ICustomer> = {};
