export interface IProductCategory {
    id?: number;
    productCategoryName?: string;
}

export class ProductCategory implements IProductCategory {
    constructor(public id?: number, public productCategoryName?: string) {}
}
