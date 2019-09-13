export interface IProductDocument {
    id?: number;
    documentNodeContentType?: string;
    documentNode?: any;
    videoUrl?: string;
    highlights?: any;
    productProductName?: string;
    productId?: number;
    cultureCultureName?: string;
    cultureId?: number;
}

export class ProductDocument implements IProductDocument {
    constructor(
        public id?: number,
        public documentNodeContentType?: string,
        public documentNode?: any,
        public videoUrl?: string,
        public highlights?: any,
        public productProductName?: string,
        public productId?: number,
        public cultureCultureName?: string,
        public cultureId?: number
    ) {}
}
