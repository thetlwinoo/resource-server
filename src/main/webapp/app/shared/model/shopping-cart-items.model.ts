export interface IShoppingCartItems {
    id?: number;
    quantity?: number;
    productProductName?: string;
    productId?: number;
    cartId?: number;
}

export class ShoppingCartItems implements IShoppingCartItems {
    constructor(
        public id?: number,
        public quantity?: number,
        public productProductName?: string,
        public productId?: number,
        public cartId?: number
    ) {}
}
