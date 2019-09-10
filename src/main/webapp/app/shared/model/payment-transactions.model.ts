export interface IPaymentTransactions {
    id?: number;
    returnedCompletedPaymentData?: any;
    orderId?: number;
}

export class PaymentTransactions implements IPaymentTransactions {
    constructor(public id?: number, public returnedCompletedPaymentData?: any, public orderId?: number) {}
}
