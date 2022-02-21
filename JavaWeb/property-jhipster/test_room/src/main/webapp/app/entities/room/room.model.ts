import { IAccommodation } from 'app/entities/accommodation/accommodation.model';
import { RoomType } from 'app/entities/enumerations/room-type.model';
import { RoomSize } from 'app/entities/enumerations/room-size.model';

export interface IRoom {
  id?: number;
  rTitle?: string;
  rType?: RoomType;
  rAcreage?: number;
  rSize?: RoomSize | null;
  rImageContentType?: string | null;
  rImage?: string | null;
  rPrice?: number | null;
  accommodation?: IAccommodation;
}

export class Room implements IRoom {
  constructor(
    public id?: number,
    public rTitle?: string,
    public rType?: RoomType,
    public rAcreage?: number,
    public rSize?: RoomSize | null,
    public rImageContentType?: string | null,
    public rImage?: string | null,
    public rPrice?: number | null,
    public accommodation?: IAccommodation
  ) {}
}

export function getRoomIdentifier(room: IRoom): number | undefined {
  return room.id;
}
