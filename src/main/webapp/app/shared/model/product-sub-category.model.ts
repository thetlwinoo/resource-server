export interface IProductSubCategory {
    id?: number;
    productSubCategoryName?: string;
    productCategoryProductCategoryName?: string;
    productCategoryId?: number;
}

export class ProductSubCategory implements IProductSubCategory {
    constructor(
        public id?: number,
        public productSubCategoryName?: string,
        public productCategoryProductCategoryName?: string,
        public productCategoryId?: number
    ) {}
}
