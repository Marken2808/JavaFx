import { IUser } from 'app/entities/user/user.model';
import { IName } from 'app/entities/name/name.model';
import { IAddress } from 'app/entities/address/address.model';
import { Gender } from 'app/entities/enumerations/gender.model';

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

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public email?: string,
    public phone?: string,
    public birth?: string,
    public gender?: Gender | null,
    public user?: IUser,
    public name?: IName,
    public address?: IAddress
  ) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
