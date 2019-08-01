import { Moment } from 'moment';

export interface IProductTransactions {
    id?: number;
    transactionOccuredWhen?: Moment;
    quantity?: number;
    productProductName?: string;
    productId?: number;
    customerId?: number;
    invoiceId?: number;
    supplierSupplierName?: string;
    supplierId?: number;
    transactionTypeId?: number;
    purchaseOrderId?: number;
}

export class ProductTransactions implements IProductTransactions {
    constructor(
        public id?: number,
        public transactionOccuredWhen?: Moment,
        public quantity?: number,
        public productProductName?: string,
        public productId?: number,
        public customerId?: number,
        public invoiceId?: number,
        public supplierSupplierName?: string,
        public supplierId?: number,
        public transactionTypeId?: number,
        public purchaseOrderId?: number
    ) {}
}
