import { Moment } from 'moment';

export interface IProducts {
    id?: number;
    productName?: string;
    productNumber?: string;
    searchDetails?: string;
    makeFlag?: boolean;
    finishedGoodsFlag?: boolean;
    color?: string;
    safetyStockLevel?: number;
    reorderPoint?: number;
    standardCost?: number;
    unitPrice?: number;
    recommendedRetailPrice?: number;
    brand?: string;
    specifySize?: string;
    weight?: number;
    daysToManufacture?: number;
    productLine?: string;
    classType?: string;
    style?: string;
    customFields?: string;
    photo?: string;
    sellStartDate?: Moment;
    sellEndDate?: Moment;
    marketingComments?: string;
    internalComments?: string;
    discontinuedDate?: Moment;
    sellCount?: number;
    productReviewId?: number;
    unitPackagePackageTypeName?: string;
    unitPackageId?: number;
    outerPackagePackageTypeName?: string;
    outerPackageId?: number;
    supplierSupplierName?: string;
    supplierId?: number;
    productSubCategoryProductSubCategoryName?: string;
    productSubCategoryId?: number;
    sizeUnitMeasureCodeUnitMeasureCode?: string;
    sizeUnitMeasureCodeId?: number;
    weightUnitMeasureCodeUnitMeasureCode?: string;
    weightUnitMeasureCodeId?: number;
    productModelProductModelName?: string;
    productModelId?: number;
}

export class Products implements IProducts {
    constructor(
        public id?: number,
        public productName?: string,
        public productNumber?: string,
        public searchDetails?: string,
        public makeFlag?: boolean,
        public finishedGoodsFlag?: boolean,
        public color?: string,
        public safetyStockLevel?: number,
        public reorderPoint?: number,
        public standardCost?: number,
        public unitPrice?: number,
        public recommendedRetailPrice?: number,
        public brand?: string,
        public specifySize?: string,
        public weight?: number,
        public daysToManufacture?: number,
        public productLine?: string,
        public classType?: string,
        public style?: string,
        public customFields?: string,
        public photo?: string,
        public sellStartDate?: Moment,
        public sellEndDate?: Moment,
        public marketingComments?: string,
        public internalComments?: string,
        public discontinuedDate?: Moment,
        public sellCount?: number,
        public productReviewId?: number,
        public unitPackagePackageTypeName?: string,
        public unitPackageId?: number,
        public outerPackagePackageTypeName?: string,
        public outerPackageId?: number,
        public supplierSupplierName?: string,
        public supplierId?: number,
        public productSubCategoryProductSubCategoryName?: string,
        public productSubCategoryId?: number,
        public sizeUnitMeasureCodeUnitMeasureCode?: string,
        public sizeUnitMeasureCodeId?: number,
        public weightUnitMeasureCodeUnitMeasureCode?: string,
        public weightUnitMeasureCodeId?: number,
        public productModelProductModelName?: string,
        public productModelId?: number
    ) {
        this.makeFlag = this.makeFlag || false;
        this.finishedGoodsFlag = this.finishedGoodsFlag || false;
    }
}
