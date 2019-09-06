export interface IProductCategory {
    id?: number;
    productCategoryName?: string;
    photoContentType?: string;
    photo?: any;
}

export class ProductCategory implements IProductCategory {
    constructor(public id?: number, public productCategoryName?: string, public photoContentType?: string, public photo?: any) {}
}
