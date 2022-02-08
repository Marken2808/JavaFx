import { IUser } from 'app/entities/user/user.model';
import { IShoppingCart } from 'app/entities/shopping-cart/shopping-cart.model';
import { IWishList } from 'app/entities/wish-list/wish-list.model';
import { IAddress } from 'app/entities/address/address.model';
import { Gender } from 'app/entities/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  telephone?: string | null;
  gender?: Gender;
  user?: IUser;
  carts?: IShoppingCart[] | null;
  wishLists?: IWishList[] | null;
  addresses?: IAddress[] | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string | null,
    public telephone?: string | null,
    public gender?: Gender,
    public user?: IUser,
    public carts?: IShoppingCart[] | null,
    public wishLists?: IWishList[] | null,
    public addresses?: IAddress[] | null
  ) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
