import { Moment } from 'moment';

export interface IProducts {
    id?: number;
    productName?: string;
    productNumber?: string;
    searchDetails?: string;
    thumbnailUrl?: string;
    sellStartDate?: Moment;
    sellEndDate?: Moment;
    warrantyPeriod?: string;
    warrantyPolicy?: string;
    sellCount?: number;
    whatInTheBox?: string;
    supplierSupplierName?: string;
    supplierId?: number;
    merchantMerchantName?: string;
    merchantId?: number;
    unitPackagePackageTypeName?: string;
    unitPackageId?: number;
    outerPackagePackageTypeName?: string;
    outerPackageId?: number;
    productModelProductModelName?: string;
    productModelId?: number;
    productCategoryName?: string;
    productCategoryId?: number;
    productBrandProductBrandName?: string;
    productBrandId?: number;
    warrantyTypeWarrantyTypeName?: string;
    warrantyTypeId?: number;
}

export class Products implements IProducts {
    constructor(
        public id?: number,
        public productName?: string,
        public productNumber?: string,
        public searchDetails?: string,
        public thumbnailUrl?: string,
        public sellStartDate?: Moment,
        public sellEndDate?: Moment,
        public warrantyPeriod?: string,
        public warrantyPolicy?: string,
        public sellCount?: number,
        public whatInTheBox?: string,
        public supplierSupplierName?: string,
        public supplierId?: number,
        public merchantMerchantName?: string,
        public merchantId?: number,
        public unitPackagePackageTypeName?: string,
        public unitPackageId?: number,
        public outerPackagePackageTypeName?: string,
        public outerPackageId?: number,
        public productModelProductModelName?: string,
        public productModelId?: number,
        public productCategoryName?: string,
        public productCategoryId?: number,
        public productBrandProductBrandName?: string,
        public productBrandId?: number,
        public warrantyTypeWarrantyTypeName?: string,
        public warrantyTypeId?: number
    ) {}
}
