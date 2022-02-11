import { IUser } from 'app/shared/model/user.model';
import { IName } from 'app/shared/model/name.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  email?: string;
  telephone?: string;
  gender?: Gender;
  user?: IUser;
  name?: IName;
}

export const defaultValue: Readonly<ICustomer> = {};
