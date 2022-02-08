import { IProduct } from 'app/entities/product/product.model';
import { ICustomer } from 'app/entities/customer/customer.model';

export interface IWishList {
  id?: number;
  title?: string;
  restricted?: boolean | null;
  products?: IProduct[] | null;
  customer?: ICustomer | null;
}

export class WishList implements IWishList {
  constructor(
    public id?: number,
    public title?: string,
    public restricted?: boolean | null,
    public products?: IProduct[] | null,
    public customer?: ICustomer | null
  ) {
    this.restricted = this.restricted ?? false;
  }
}

export function getWishListIdentifier(wishList: IWishList): number | undefined {
  return wishList.id;
}
