import { ICustomer } from 'app/entities/customer/customer.model';
import { Title } from 'app/entities/enumerations/title.model';

export interface IName {
  id?: number;
  title?: Title;
  firstName?: string;
  middleName?: string | null;
  lastName?: string;
  displayName?: string;
  customers?: ICustomer[] | null;
}

export class Name implements IName {
  constructor(
    public id?: number,
    public title?: Title,
    public firstName?: string,
    public middleName?: string | null,
    public lastName?: string,
    public displayName?: string,
    public customers?: ICustomer[] | null
  ) {}
}

export function getNameIdentifier(name: IName): number | undefined {
  return name.id;
}
