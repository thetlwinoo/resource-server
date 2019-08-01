export interface IStockItemStockGroups {
    id?: number;
    stockGroupStockGroupName?: string;
    stockGroupId?: number;
    productProductName?: string;
    productId?: number;
}

export class StockItemStockGroups implements IStockItemStockGroups {
    constructor(
        public id?: number,
        public stockGroupStockGroupName?: string,
        public stockGroupId?: number,
        public productProductName?: string,
        public productId?: number
    ) {}
}
