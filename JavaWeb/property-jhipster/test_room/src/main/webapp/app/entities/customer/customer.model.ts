import { IUser } from 'app/entities/user/user.model';
import { IProperty } from 'app/entities/property/property.model';
import { IName } from 'app/entities/name/name.model';
import { Gender } from 'app/entities/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  email?: string;
  phone?: string;
  birth?: string;
  gender?: Gender | null;
  user?: IUser;
  properties?: IProperty[];
  name?: IName;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public email?: string,
    public phone?: string,
    public birth?: string,
    public gender?: Gender | null,
    public user?: IUser,
    public properties?: IProperty[],
    public name?: IName
  ) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
