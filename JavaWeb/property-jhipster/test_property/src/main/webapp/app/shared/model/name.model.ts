import { Title } from 'app/shared/model/enumerations/title.model';

export interface IName {
  id?: number;
  title?: Title;
  firstName?: string;
  middleName?: string | null;
  lastName?: string;
  displayName?: string;
}

export const defaultValue: Readonly<IName> = {};
