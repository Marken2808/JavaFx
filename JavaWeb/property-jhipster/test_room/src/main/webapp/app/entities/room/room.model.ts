import { IAccommodation } from 'app/entities/accommodation/accommodation.model';
import { RoomType } from 'app/entities/enumerations/room-type.model';

export interface IRoom {
  id?: number;
  title?: string;
  type?: RoomType | null;
  accommodations?: IAccommodation[] | null;
}

export class Room implements IRoom {
  constructor(public id?: number, public title?: string, public type?: RoomType | null, public accommodations?: IAccommodation[] | null) {}
}

export function getRoomIdentifier(room: IRoom): number | undefined {
  return room.id;
}
