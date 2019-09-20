import { IStockItems } from 'app/shared/model/stock-items.model';

export interface IProducts {
    id?: number;
    productName?: string;
    productNumber?: string;
    searchDetails?: string;
    thumbnailUrl?: string;
    warrantyPeriod?: string;
    warrantyPolicy?: string;
    sellCount?: number;
    whatInTheBox?: string;
    stockItemLists?: IStockItems[];
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
        public warrantyPeriod?: string,
        public warrantyPolicy?: string,
        public sellCount?: number,
        public whatInTheBox?: string,
        public stockItemLists?: IStockItems[],
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
