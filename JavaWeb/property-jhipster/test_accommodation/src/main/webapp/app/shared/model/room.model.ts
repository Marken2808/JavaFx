import { IAccommodation } from 'app/shared/model/accommodation.model';
import { RoomType } from 'app/shared/model/enumerations/room-type.model';

export interface IRoom {
  id?: number;
  title?: string;
  acreage?: number;
  imageContentType?: string | null;
  image?: string | null;
  type?: RoomType | null;
  price?: number | null;
  accommodation?: IAccommodation | null;
}

export const defaultValue: Readonly<IRoom> = {};
