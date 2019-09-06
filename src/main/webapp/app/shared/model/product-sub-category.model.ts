export interface IProductSubCategory {
    id?: number;
    productSubCategoryName?: string;
    photoContentType?: string;
    photo?: any;
    productCategoryProductCategoryName?: string;
    productCategoryId?: number;
}

export class ProductSubCategory implements IProductSubCategory {
    constructor(
        public id?: number,
        public productSubCategoryName?: string,
        public photoContentType?: string,
        public photo?: any,
        public productCategoryProductCategoryName?: string,
        public productCategoryId?: number
    ) {}
}
