export interface IAddress {
  id?: number;
  numberic?: string | null;
  street?: string;
  county?: string | null;
  city?: string | null;
  postcode?: string | null;
  country?: string | null;
}

export const defaultValue: Readonly<IAddress> = {};
