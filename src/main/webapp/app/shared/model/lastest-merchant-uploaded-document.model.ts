export interface ILastestMerchantUploadedDocument {
    id?: number;
    productCreateTemplateContentType?: string;
    productCreateTemplate?: any;
}

export class LastestMerchantUploadedDocument implements ILastestMerchantUploadedDocument {
    constructor(public id?: number, public productCreateTemplateContentType?: string, public productCreateTemplate?: any) {}
}
