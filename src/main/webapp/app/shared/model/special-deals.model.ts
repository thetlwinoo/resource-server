import { Moment } from 'moment';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { IOrders } from 'app/shared/model/orders.model';

export interface ISpecialDeals {
    id?: number;
    dealDescription?: string;
    startDate?: Moment;
    endDate?: Moment;
    discountAmount?: number;
    discountPercentage?: number;
    discountCode?: string;
    unitPrice?: number;
    cartDiscounts?: IShoppingCarts[];
    orderDiscounts?: IOrders[];
    buyingGroupBuyingGroupName?: string;
    buyingGroupId?: number;
    customerCategoryCustomerCategoryName?: string;
    customerCategoryId?: number;
    customerId?: number;
    stockGroupStockGroupName?: string;
    stockGroupId?: number;
    productProductName?: string;
    productId?: number;
}

export class SpecialDeals implements ISpecialDeals {
    constructor(
        public id?: number,
        public dealDescription?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public discountAmount?: number,
        public discountPercentage?: number,
        public discountCode?: string,
        public unitPrice?: number,
        public cartDiscounts?: IShoppingCarts[],
        public orderDiscounts?: IOrders[],
        public buyingGroupBuyingGroupName?: string,
        public buyingGroupId?: number,
        public customerCategoryCustomerCategoryName?: string,
        public customerCategoryId?: number,
        public customerId?: number,
        public stockGroupStockGroupName?: string,
        public stockGroupId?: number,
        public productProductName?: string,
        public productId?: number
    ) {}
}
