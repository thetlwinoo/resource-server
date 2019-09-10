package com.resource.server.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PaymentTransactions.
 */
@Entity
@Table(name = "payment_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentTransactions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Column(name = "returned_completed_payment_data", nullable = false)
    private String returnedCompletedPaymentData;

    @OneToOne
    @JoinColumn(unique = true)
    private Orders order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnedCompletedPaymentData() {
        return returnedCompletedPaymentData;
    }

    public PaymentTransactions returnedCompletedPaymentData(String returnedCompletedPaymentData) {
        this.returnedCompletedPaymentData = returnedCompletedPaymentData;
        return this;
    }

    public void setReturnedCompletedPaymentData(String returnedCompletedPaymentData) {
        this.returnedCompletedPaymentData = returnedCompletedPaymentData;
    }

    public Orders getOrder() {
        return order;
    }

    public PaymentTransactions order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentTransactions paymentTransactions = (PaymentTransactions) o;
        if (paymentTransactions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentTransactions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentTransactions{" +
            "id=" + getId() +
            ", returnedCompletedPaymentData='" + getReturnedCompletedPaymentData() + "'" +
            "}";
    }
}
