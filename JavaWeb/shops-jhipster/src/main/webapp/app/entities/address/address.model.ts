import { ICustomer } from 'app/entities/customer/customer.model';

export interface IAddress {
  id?: number;
  address1?: string | null;
  address2?: string | null;
  city?: string | null;
  postcode?: string;
  country?: string;
  customer?: ICustomer | null;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public address1?: string | null,
    public address2?: string | null,
    public city?: string | null,
    public postcode?: string,
    public country?: string,
    public customer?: ICustomer | null
  ) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
