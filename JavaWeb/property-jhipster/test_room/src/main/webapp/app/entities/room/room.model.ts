import { IProperty } from 'app/entities/property/property.model';
import { RoomType } from 'app/entities/enumerations/room-type.model';

export interface IRoom {
  id?: number;
  title?: string;
  acreage?: number;
  imageContentType?: string | null;
  image?: string | null;
  type?: RoomType | null;
  price?: number | null;
  properties?: IProperty[] | null;
}

export class Room implements IRoom {
  constructor(
    public id?: number,
    public title?: string,
    public acreage?: number,
    public imageContentType?: string | null,
    public image?: string | null,
    public type?: RoomType | null,
    public price?: number | null,
    public properties?: IProperty[] | null
  ) {}
}

export function getRoomIdentifier(room: IRoom): number | undefined {
  return room.id;
}
