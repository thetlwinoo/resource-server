import { Moment } from 'moment';

export interface IStockItemTransactions {
    id?: number;
    transactionOccurredWhen?: Moment;
    quantity?: number;
    customerId?: number;
    invoiceId?: number;
    purchaseOrderId?: number;
    productProductName?: string;
    productId?: number;
    supplierSupplierName?: string;
    supplierId?: number;
    transactionTypeTransactionTypeName?: string;
    transactionTypeId?: number;
}

export class StockItemTransactions implements IStockItemTransactions {
    constructor(
        public id?: number,
        public transactionOccurredWhen?: Moment,
        public quantity?: number,
        public customerId?: number,
        public invoiceId?: number,
        public purchaseOrderId?: number,
        public productProductName?: string,
        public productId?: number,
        public supplierSupplierName?: string,
        public supplierId?: number,
        public transactionTypeTransactionTypeName?: string,
        public transactionTypeId?: number
    ) {}
}
