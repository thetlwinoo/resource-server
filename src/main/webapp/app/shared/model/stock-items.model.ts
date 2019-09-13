import { Moment } from 'moment';

export interface IStockItems {
    id?: number;
    stockItemName?: string;
    sellerSKU?: string;
    generatedSKU?: string;
    barcode?: string;
    unitPrice?: number;
    recommendedRetailPrice?: number;
    quantityPerOuter?: number;
    typicalWeightPerUnit?: number;
    typicalLengthPerUnit?: number;
    typicalWidthPerUnit?: number;
    typicalHeightPerUnit?: number;
    marketingComments?: string;
    internalComments?: string;
    discontinuedDate?: Moment;
    sellCount?: number;
    customFields?: string;
    thumbnailUrl?: string;
    reviewLineId?: number;
    productProductName?: string;
    productId?: number;
    lengthUnitMeasureCodeUnitMeasureCode?: string;
    lengthUnitMeasureCodeId?: number;
    weightUnitMeasureCodeUnitMeasureCode?: string;
    weightUnitMeasureCodeId?: number;
    widthUnitMeasureCodeUnitMeasureCode?: string;
    widthUnitMeasureCodeId?: number;
    heightUnitMeasureCodeUnitMeasureCode?: string;
    heightUnitMeasureCodeId?: number;
    productAttributeValue?: string;
    productAttributeId?: number;
    productOptionValue?: string;
    productOptionId?: number;
    stockItemHoldingId?: number;
}

export class StockItems implements IStockItems {
    constructor(
        public id?: number,
        public stockItemName?: string,
        public sellerSKU?: string,
        public generatedSKU?: string,
        public barcode?: string,
        public unitPrice?: number,
        public recommendedRetailPrice?: number,
        public quantityPerOuter?: number,
        public typicalWeightPerUnit?: number,
        public typicalLengthPerUnit?: number,
        public typicalWidthPerUnit?: number,
        public typicalHeightPerUnit?: number,
        public marketingComments?: string,
        public internalComments?: string,
        public discontinuedDate?: Moment,
        public sellCount?: number,
        public customFields?: string,
        public thumbnailUrl?: string,
        public reviewLineId?: number,
        public productProductName?: string,
        public productId?: number,
        public lengthUnitMeasureCodeUnitMeasureCode?: string,
        public lengthUnitMeasureCodeId?: number,
        public weightUnitMeasureCodeUnitMeasureCode?: string,
        public weightUnitMeasureCodeId?: number,
        public widthUnitMeasureCodeUnitMeasureCode?: string,
        public widthUnitMeasureCodeId?: number,
        public heightUnitMeasureCodeUnitMeasureCode?: string,
        public heightUnitMeasureCodeId?: number,
        public productAttributeValue?: string,
        public productAttributeId?: number,
        public productOptionValue?: string,
        public productOptionId?: number,
        public stockItemHoldingId?: number
    ) {}
}
