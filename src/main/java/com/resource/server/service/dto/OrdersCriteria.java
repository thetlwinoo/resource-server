package com.resource.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Orders entity. This class is used in OrdersResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /orders?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdersCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter orderDate;

    private LocalDateFilter dueDate;

    private LocalDateFilter shipDate;

    private IntegerFilter paymentStatus;

    private IntegerFilter orderFlag;

    private StringFilter orderNumber;

    private FloatFilter subTotal;

    private FloatFilter taxAmount;

    private FloatFilter frieight;

    private FloatFilter totalDue;

    private StringFilter comments;

    private StringFilter deliveryInstructions;

    private StringFilter internalComments;

    private LocalDateFilter pickingCompletedWhen;

    private LongFilter orderOnReviewId;

    private LongFilter orderLineListId;

    private LongFilter customerId;

    private LongFilter shipToAddressId;

    private LongFilter billToAddressId;

    private LongFilter shipMethodId;

    private LongFilter currencyRateId;

    private LongFilter paymentTransactionId;

    private LongFilter specialDealsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateFilter orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateFilter getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDateFilter shipDate) {
        this.shipDate = shipDate;
    }

    public IntegerFilter getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(IntegerFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public IntegerFilter getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(IntegerFilter orderFlag) {
        this.orderFlag = orderFlag;
    }

    public StringFilter getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(StringFilter orderNumber) {
        this.orderNumber = orderNumber;
    }

    public FloatFilter getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(FloatFilter subTotal) {
        this.subTotal = subTotal;
    }

    public FloatFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(FloatFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public FloatFilter getFrieight() {
        return frieight;
    }

    public void setFrieight(FloatFilter frieight) {
        this.frieight = frieight;
    }

    public FloatFilter getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(FloatFilter totalDue) {
        this.totalDue = totalDue;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(StringFilter deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public StringFilter getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(StringFilter internalComments) {
        this.internalComments = internalComments;
    }

    public LocalDateFilter getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(LocalDateFilter pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public LongFilter getOrderOnReviewId() {
        return orderOnReviewId;
    }

    public void setOrderOnReviewId(LongFilter orderOnReviewId) {
        this.orderOnReviewId = orderOnReviewId;
    }

    public LongFilter getOrderLineListId() {
        return orderLineListId;
    }

    public void setOrderLineListId(LongFilter orderLineListId) {
        this.orderLineListId = orderLineListId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getShipToAddressId() {
        return shipToAddressId;
    }

    public void setShipToAddressId(LongFilter shipToAddressId) {
        this.shipToAddressId = shipToAddressId;
    }

    public LongFilter getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(LongFilter billToAddressId) {
        this.billToAddressId = billToAddressId;
    }

    public LongFilter getShipMethodId() {
        return shipMethodId;
    }

    public void setShipMethodId(LongFilter shipMethodId) {
        this.shipMethodId = shipMethodId;
    }

    public LongFilter getCurrencyRateId() {
        return currencyRateId;
    }

    public void setCurrencyRateId(LongFilter currencyRateId) {
        this.currencyRateId = currencyRateId;
    }

    public LongFilter getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(LongFilter paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public LongFilter getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(LongFilter specialDealsId) {
        this.specialDealsId = specialDealsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdersCriteria that = (OrdersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(shipDate, that.shipDate) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(orderFlag, that.orderFlag) &&
            Objects.equals(orderNumber, that.orderNumber) &&
            Objects.equals(subTotal, that.subTotal) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(frieight, that.frieight) &&
            Objects.equals(totalDue, that.totalDue) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(deliveryInstructions, that.deliveryInstructions) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(pickingCompletedWhen, that.pickingCompletedWhen) &&
            Objects.equals(orderOnReviewId, that.orderOnReviewId) &&
            Objects.equals(orderLineListId, that.orderLineListId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(shipToAddressId, that.shipToAddressId) &&
            Objects.equals(billToAddressId, that.billToAddressId) &&
            Objects.equals(shipMethodId, that.shipMethodId) &&
            Objects.equals(currencyRateId, that.currencyRateId) &&
            Objects.equals(paymentTransactionId, that.paymentTransactionId) &&
            Objects.equals(specialDealsId, that.specialDealsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderDate,
        dueDate,
        shipDate,
        paymentStatus,
        orderFlag,
        orderNumber,
        subTotal,
        taxAmount,
        frieight,
        totalDue,
        comments,
        deliveryInstructions,
        internalComments,
        pickingCompletedWhen,
        orderOnReviewId,
        orderLineListId,
        customerId,
        shipToAddressId,
        billToAddressId,
        shipMethodId,
        currencyRateId,
        paymentTransactionId,
        specialDealsId
        );
    }

    @Override
    public String toString() {
        return "OrdersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (shipDate != null ? "shipDate=" + shipDate + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (orderFlag != null ? "orderFlag=" + orderFlag + ", " : "") +
                (orderNumber != null ? "orderNumber=" + orderNumber + ", " : "") +
                (subTotal != null ? "subTotal=" + subTotal + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (frieight != null ? "frieight=" + frieight + ", " : "") +
                (totalDue != null ? "totalDue=" + totalDue + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (deliveryInstructions != null ? "deliveryInstructions=" + deliveryInstructions + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (pickingCompletedWhen != null ? "pickingCompletedWhen=" + pickingCompletedWhen + ", " : "") +
                (orderOnReviewId != null ? "orderOnReviewId=" + orderOnReviewId + ", " : "") +
                (orderLineListId != null ? "orderLineListId=" + orderLineListId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (shipToAddressId != null ? "shipToAddressId=" + shipToAddressId + ", " : "") +
                (billToAddressId != null ? "billToAddressId=" + billToAddressId + ", " : "") +
                (shipMethodId != null ? "shipMethodId=" + shipMethodId + ", " : "") +
                (currencyRateId != null ? "currencyRateId=" + currencyRateId + ", " : "") +
                (paymentTransactionId != null ? "paymentTransactionId=" + paymentTransactionId + ", " : "") +
                (specialDealsId != null ? "specialDealsId=" + specialDealsId + ", " : "") +
            "}";
    }

}
