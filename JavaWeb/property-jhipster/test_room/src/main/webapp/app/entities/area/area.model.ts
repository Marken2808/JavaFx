import { IRoom } from 'app/entities/room/room.model';
import { AreaType } from 'app/entities/enumerations/area-type.model';

export interface IArea {
  id?: number;
  title?: string;
  type?: AreaType;
  rooms?: IRoom[] | null;
}

export class Area implements IArea {
  constructor(public id?: number, public title?: string, public type?: AreaType, public rooms?: IRoom[] | null) {}
}

export function getAreaIdentifier(area: IArea): number | undefined {
  return area.id;
}
