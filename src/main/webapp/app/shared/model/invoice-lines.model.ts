export interface IInvoiceLines {
    id?: number;
    description?: string;
    quantity?: number;
    unitPrice?: number;
    taxRate?: number;
    taxAmount?: number;
    lineProfit?: number;
    extendedPrice?: number;
    packageTypePackageTypeName?: string;
    packageTypeId?: number;
    productId?: number;
    invoiceId?: number;
}

export class InvoiceLines implements IInvoiceLines {
    constructor(
        public id?: number,
        public description?: string,
        public quantity?: number,
        public unitPrice?: number,
        public taxRate?: number,
        public taxAmount?: number,
        public lineProfit?: number,
        public extendedPrice?: number,
        public packageTypePackageTypeName?: string,
        public packageTypeId?: number,
        public productId?: number,
        public invoiceId?: number
    ) {}
}
