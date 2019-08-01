import { Moment } from 'moment';

export interface IStockGroups {
    id?: number;
    stockGroupName?: string;
    validFrom?: Moment;
    validTo?: Moment;
}

export class StockGroups implements IStockGroups {
    constructor(public id?: number, public stockGroupName?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
