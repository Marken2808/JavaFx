import { IArea } from 'app/entities/area/area.model';
import { IAccommodation } from 'app/entities/accommodation/accommodation.model';
import { RoomType } from 'app/entities/enumerations/room-type.model';

export interface IRoom {
  id?: number;
  title?: string;
  type?: RoomType;
  acreage?: number;
  imageContentType?: string | null;
  image?: string | null;
  price?: number | null;
  area?: IArea;
  accommodations?: IAccommodation[] | null;
}

export class Room implements IRoom {
  constructor(
    public id?: number,
    public title?: string,
    public type?: RoomType,
    public acreage?: number,
    public imageContentType?: string | null,
    public image?: string | null,
    public price?: number | null,
    public area?: IArea,
    public accommodations?: IAccommodation[] | null
  ) {}
}

export function getRoomIdentifier(room: IRoom): number | undefined {
  return room.id;
}
