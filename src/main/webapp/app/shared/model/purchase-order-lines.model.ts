import { Moment } from 'moment';

export interface IPurchaseOrderLines {
    id?: number;
    ordersOuters?: number;
    description?: string;
    receivedOuters?: number;
    expectedUnitPricePerOuter?: number;
    lastReceiptDate?: Moment;
    isOrderLineFinalized?: boolean;
    productProductName?: string;
    productId?: number;
    packageTypePackageTypeName?: string;
    packageTypeId?: number;
    purchaseOrderId?: number;
}

export class PurchaseOrderLines implements IPurchaseOrderLines {
    constructor(
        public id?: number,
        public ordersOuters?: number,
        public description?: string,
        public receivedOuters?: number,
        public expectedUnitPricePerOuter?: number,
        public lastReceiptDate?: Moment,
        public isOrderLineFinalized?: boolean,
        public productProductName?: string,
        public productId?: number,
        public packageTypePackageTypeName?: string,
        public packageTypeId?: number,
        public purchaseOrderId?: number
    ) {
        this.isOrderLineFinalized = this.isOrderLineFinalized || false;
    }
}
