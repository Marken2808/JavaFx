import { IProduct } from 'app/entities/product/product.model';
import { ICart } from 'app/entities/cart/cart.model';

export interface IOrder {
  id?: number;
  quantity?: number;
  totalPrice?: number;
  product?: IProduct;
  cart?: ICart;
}

export class Order implements IOrder {
  constructor(public id?: number, public quantity?: number, public totalPrice?: number, public product?: IProduct, public cart?: ICart) {}
}

export function getOrderIdentifier(order: IOrder): number | undefined {
  return order.id;
}
