import dayjs from 'dayjs/esm';
import { IOrder } from 'app/entities/order/order.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { OrderStatus } from 'app/entities/enumerations/order-status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

export interface ICart {
  id?: number;
  placedDate?: dayjs.Dayjs;
  status?: OrderStatus;
  totalPrice?: number;
  paymentMethod?: PaymentMethod;
  paymentReference?: string | null;
  orders?: IOrder[];
  customer?: ICustomer;
}

export class Cart implements ICart {
  constructor(
    public id?: number,
    public placedDate?: dayjs.Dayjs,
    public status?: OrderStatus,
    public totalPrice?: number,
    public paymentMethod?: PaymentMethod,
    public paymentReference?: string | null,
    public orders?: IOrder[],
    public customer?: ICustomer
  ) {}
}

export function getCartIdentifier(cart: ICart): number | undefined {
  return cart.id;
}
