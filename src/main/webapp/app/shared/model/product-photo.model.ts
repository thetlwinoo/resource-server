export interface IProductPhoto {
    id?: number;
    thumbnailPhoto?: string;
    originalPhoto?: string;
    bannerTallPhoto?: string;
    bannerWidePhoto?: string;
    circlePhoto?: string;
    sharpenedPhoto?: string;
    squarePhoto?: string;
    watermarkPhoto?: string;
    priority?: number;
    defaultInd?: boolean;
    deleteToken?: string;
    productProductName?: string;
    productId?: number;
}

export class ProductPhoto implements IProductPhoto {
    constructor(
        public id?: number,
        public thumbnailPhoto?: string,
        public originalPhoto?: string,
        public bannerTallPhoto?: string,
        public bannerWidePhoto?: string,
        public circlePhoto?: string,
        public sharpenedPhoto?: string,
        public squarePhoto?: string,
        public watermarkPhoto?: string,
        public priority?: number,
        public defaultInd?: boolean,
        public deleteToken?: string,
        public productProductName?: string,
        public productId?: number
    ) {
        this.defaultInd = this.defaultInd || false;
    }
}
