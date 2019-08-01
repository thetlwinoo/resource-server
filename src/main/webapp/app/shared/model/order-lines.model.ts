import { Moment } from 'moment';

export interface IOrderLines {
    id?: number;
    carrierTrackingNumber?: string;
    quantity?: number;
    unitPrice?: number;
    unitPriceDiscount?: number;
    lineTotal?: number;
    taxRate?: number;
    pickedQuantity?: number;
    pickingCompletedWhen?: Moment;
    packageTypeId?: number;
    productProductName?: string;
    productId?: number;
    orderId?: number;
}

export class OrderLines implements IOrderLines {
    constructor(
        public id?: number,
        public carrierTrackingNumber?: string,
        public quantity?: number,
        public unitPrice?: number,
        public unitPriceDiscount?: number,
        public lineTotal?: number,
        public taxRate?: number,
        public pickedQuantity?: number,
        public pickingCompletedWhen?: Moment,
        public packageTypeId?: number,
        public productProductName?: string,
        public productId?: number,
        public orderId?: number
    ) {}
}
