export interface IProductDocument {
    id?: number;
    documentNode?: any;
    productProductName?: string;
    productId?: number;
    cultureCultureName?: string;
    cultureId?: number;
}

export class ProductDocument implements IProductDocument {
    constructor(
        public id?: number,
        public documentNode?: any,
        public productProductName?: string,
        public productId?: number,
        public cultureCultureName?: string,
        public cultureId?: number
    ) {}
}
