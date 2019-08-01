export interface IProductDescription {
    id?: number;
    description?: string;
    productModelId?: number;
}

export class ProductDescription implements IProductDescription {
    constructor(public id?: number, public description?: string, public productModelId?: number) {}
}
