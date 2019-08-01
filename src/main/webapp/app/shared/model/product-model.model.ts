import { IProductDescription } from 'app/shared/model/product-description.model';

export interface IProductModel {
    id?: number;
    productModelName?: string;
    calalogDescription?: string;
    instructions?: string;
    descriptions?: IProductDescription[];
}

export class ProductModel implements IProductModel {
    constructor(
        public id?: number,
        public productModelName?: string,
        public calalogDescription?: string,
        public instructions?: string,
        public descriptions?: IProductDescription[]
    ) {}
}
