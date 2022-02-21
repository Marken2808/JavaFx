import { IProperty } from 'app/entities/property/property.model';

export interface IProject {
  id?: number;
  title?: string;
  price?: number;
  imageContentType?: string | null;
  image?: string | null;
  property?: IProperty | null;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public title?: string,
    public price?: number,
    public imageContentType?: string | null,
    public image?: string | null,
    public property?: IProperty | null
  ) {}
}

export function getProjectIdentifier(project: IProject): number | undefined {
  return project.id;
}
