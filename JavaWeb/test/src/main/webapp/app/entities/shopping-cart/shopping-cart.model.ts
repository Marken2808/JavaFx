import dayjs from 'dayjs/esm';
import { IProductOrder } from 'app/entities/product-order/product-order.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { OrderStatus } from 'app/entities/enumerations/order-status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

export interface IShoppingCart {
  id?: number;
  placedDate?: dayjs.Dayjs;
  status?: OrderStatus;
  totalPrice?: number;
  paymentMethod?: PaymentMethod;
  paymentReference?: string | null;
  orders?: IProductOrder[] | null;
  customer?: ICustomer;
}

export class ShoppingCart implements IShoppingCart {
  constructor(
    public id?: number,
    public placedDate?: dayjs.Dayjs,
    public status?: OrderStatus,
    public totalPrice?: number,
    public paymentMethod?: PaymentMethod,
    public paymentReference?: string | null,
    public orders?: IProductOrder[] | null,
    public customer?: ICustomer
  ) {}
}

export function getShoppingCartIdentifier(shoppingCart: IShoppingCart): number | undefined {
  return shoppingCart.id;
}
