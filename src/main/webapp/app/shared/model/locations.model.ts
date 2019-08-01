export interface ILocations {
    id?: number;
    locationName?: string;
    costRate?: number;
    availability?: number;
}

export class Locations implements ILocations {
    constructor(public id?: number, public locationName?: string, public costRate?: number, public availability?: number) {}
}
