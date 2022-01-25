import { IPost } from 'app/shared/model/post.model';

export interface ITag {
  id?: number;
  name?: string;
  blogs?: IPost[] | null;
}

export const defaultValue: Readonly<ITag> = {};
