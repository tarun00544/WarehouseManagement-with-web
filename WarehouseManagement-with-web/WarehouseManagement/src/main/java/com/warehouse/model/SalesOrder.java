package com.warehouse.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * SalesOrder - Model class representing a sales order
 */
public class SalesOrder {
    private int salesOrderId;
    private String orderNumber;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private LocalDateTime orderDate;
    private LocalDate deliveryDate;
    private BigDecimal totalAmount;
    private String status;
    private String notes;
    private String createdBy;
    private LocalDateTime lastUpdated;

    // Constructors
    public SalesOrder() {}

    public SalesOrder(String orderNumber, String customerName, LocalDate deliveryDate) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.deliveryDate = deliveryDate;
        this.status = "PENDING";
        this.orderDate = LocalDateTime.now();
        this.totalAmount = BigDecimal.ZERO;
    }

    // Getters and Setters
    public int getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(int salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return String.format("SalesOrder{id=%d, orderNumber='%s', customer='%s', status='%s', totalAmount=%.2f}",
                salesOrderId, orderNumber, customerName, status, totalAmount);
    }
}
