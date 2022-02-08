import dayjs from 'dayjs/esm';
import { IWishList } from 'app/entities/wish-list/wish-list.model';
import { ICategory } from 'app/entities/category/category.model';
import { Size } from 'app/entities/enumerations/size.model';

export interface IProduct {
  id?: number;
  title?: string;
  keywords?: string | null;
  description?: string | null;
  price?: number;
  productSize?: Size;
  imageContentType?: string | null;
  image?: string | null;
  rating?: number | null;
  dateAdded?: dayjs.Dayjs | null;
  dateModified?: dayjs.Dayjs | null;
  wishList?: IWishList;
  categories?: ICategory[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public title?: string,
    public keywords?: string | null,
    public description?: string | null,
    public price?: number,
    public productSize?: Size,
    public imageContentType?: string | null,
    public image?: string | null,
    public rating?: number | null,
    public dateAdded?: dayjs.Dayjs | null,
    public dateModified?: dayjs.Dayjs | null,
    public wishList?: IWishList,
    public categories?: ICategory[] | null
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
