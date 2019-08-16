export interface ICloudinary {
    cloud_name?: string;
    resource_type?: string;
    type?: string;
    transformations?: string;
    version?: string;
    public_id?: string;
    format?: string;
}

export class CloudinaryModel implements ICloudinary {
    constructor(
        public cloud_name?: string,
        public resource_type?: string,
        public type?: string,
        public transformations?: string,
        public version?: string,
        public public_id?: string,
        public format?: string
    ) {}
}
