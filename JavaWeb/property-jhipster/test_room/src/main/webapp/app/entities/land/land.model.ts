import { IProperty } from 'app/entities/property/property.model';

export interface ILand {
  id?: number;
  title?: string;
  price?: number;
  imageContentType?: string | null;
  image?: string | null;
  property?: IProperty | null;
}

export class Land implements ILand {
  constructor(
    public id?: number,
    public title?: string,
    public price?: number,
    public imageContentType?: string | null,
    public image?: string | null,
    public property?: IProperty | null
  ) {}
}

export function getLandIdentifier(land: ILand): number | undefined {
  return land.id;
}
