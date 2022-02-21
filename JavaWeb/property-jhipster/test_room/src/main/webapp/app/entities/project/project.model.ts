export interface IProject {
  id?: number;
  title?: string;
  price?: number;
}

export class Project implements IProject {
  constructor(public id?: number, public title?: string, public price?: number) {}
}

export function getProjectIdentifier(project: IProject): number | undefined {
  return project.id;
}
