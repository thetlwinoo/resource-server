import { Moment } from 'moment';

export interface IStockItemTransactions {
    id?: number;
    transactionOccurredWhen?: Moment;
    quantity?: number;
    stockItemStockItemName?: string;
    stockItemId?: number;
    customerId?: number;
    invoiceId?: number;
    supplierSupplierName?: string;
    supplierId?: number;
    transactionTypeId?: number;
    purchaseOrderId?: number;
}

export class StockItemTransactions implements IStockItemTransactions {
    constructor(
        public id?: number,
        public transactionOccurredWhen?: Moment,
        public quantity?: number,
        public stockItemStockItemName?: string,
        public stockItemId?: number,
        public customerId?: number,
        public invoiceId?: number,
        public supplierSupplierName?: string,
        public supplierId?: number,
        public transactionTypeId?: number,
        public purchaseOrderId?: number
    ) {}
}
