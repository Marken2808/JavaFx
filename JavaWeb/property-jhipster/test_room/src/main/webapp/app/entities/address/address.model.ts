import { IProperty } from 'app/entities/property/property.model';

export interface IAddress {
  id?: number;
  numberic?: string | null;
  street?: string;
  county?: string | null;
  city?: string | null;
  postcode?: string | null;
  country?: string | null;
  property?: IProperty;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public numberic?: string | null,
    public street?: string,
    public county?: string | null,
    public city?: string | null,
    public postcode?: string | null,
    public country?: string | null,
    public property?: IProperty
  ) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
