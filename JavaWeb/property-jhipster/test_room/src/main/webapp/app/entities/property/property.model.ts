import { IUser } from 'app/entities/user/user.model';
import { PropertyType } from 'app/entities/enumerations/property-type.model';
import { PropertyStatus } from 'app/entities/enumerations/property-status.model';

export interface IProperty {
  id?: number;
  title?: string;
  type?: PropertyType;
  status?: PropertyStatus;
  imageContentType?: string | null;
  image?: string | null;
  isUrgent?: boolean | null;
  user?: IUser | null;
}

export class Property implements IProperty {
  constructor(
    public id?: number,
    public title?: string,
    public type?: PropertyType,
    public status?: PropertyStatus,
    public imageContentType?: string | null,
    public image?: string | null,
    public isUrgent?: boolean | null,
    public user?: IUser | null
  ) {
    this.isUrgent = this.isUrgent ?? false;
  }
}

export function getPropertyIdentifier(property: IProperty): number | undefined {
  return property.id;
}
