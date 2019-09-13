export interface IPaymentTransactions {
    id?: number;
    returnedCompletedPaymentData?: any;
    paymentOnOrderId?: number;
}

export class PaymentTransactions implements IPaymentTransactions {
    constructor(public id?: number, public returnedCompletedPaymentData?: any, public paymentOnOrderId?: number) {}
}
