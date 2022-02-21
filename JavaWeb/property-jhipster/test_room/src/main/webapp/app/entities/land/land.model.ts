export interface ILand {
  id?: number;
  title?: string;
  price?: number;
}

export class Land implements ILand {
  constructor(public id?: number, public title?: string, public price?: number) {}
}

export function getLandIdentifier(land: ILand): number | undefined {
  return land.id;
}
