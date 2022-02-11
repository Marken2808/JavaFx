import { ICustomer } from 'app/shared/model/customer.model';
import { Title } from 'app/shared/model/enumerations/title.model';

export interface IName {
  id?: number;
  title?: Title;
  firstName?: string;
  middleName?: string | null;
  lastName?: string;
  displayName?: string;
  customer?: ICustomer;
}

export const defaultValue: Readonly<IName> = {};
