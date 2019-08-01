package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the PaymentTransactions entity.
 */
public class PaymentTransactionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    
    @Lob
    private String returnedCompletedPaymentData;


    private Long paymentOnOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnedCompletedPaymentData() {
        return returnedCompletedPaymentData;
    }

    public void setReturnedCompletedPaymentData(String returnedCompletedPaymentData) {
        this.returnedCompletedPaymentData = returnedCompletedPaymentData;
    }

    public Long getPaymentOnOrderId() {
        return paymentOnOrderId;
    }

    public void setPaymentOnOrderId(Long ordersId) {
        this.paymentOnOrderId = ordersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentTransactionsDTO paymentTransactionsDTO = (PaymentTransactionsDTO) o;
        if (paymentTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentTransactionsDTO{" +
            "id=" + getId() +
            ", returnedCompletedPaymentData='" + getReturnedCompletedPaymentData() + "'" +
            ", paymentOnOrder=" + getPaymentOnOrderId() +
            "}";
    }
}
