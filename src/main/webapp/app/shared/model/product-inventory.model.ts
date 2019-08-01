export interface IProductInventory {
    id?: number;
    shelf?: string;
    bin?: number;
    quantity?: number;
    productProductName?: string;
    productId?: number;
    locationLocationName?: string;
    locationId?: number;
}

export class ProductInventory implements IProductInventory {
    constructor(
        public id?: number,
        public shelf?: string,
        public bin?: number,
        public quantity?: number,
        public productProductName?: string,
        public productId?: number,
        public locationLocationName?: string,
        public locationId?: number
    ) {}
}
