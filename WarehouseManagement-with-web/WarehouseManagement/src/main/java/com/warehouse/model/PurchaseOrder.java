package com.warehouse.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * PurchaseOrder - Model class representing a purchase order
 */
public class PurchaseOrder {
    private int poId;
    private String poNumber;
    private int supplierId;
    private String supplierName;
    private LocalDateTime orderDate;
    private LocalDate expectedDeliveryDate;
    private LocalDate actualDeliveryDate;
    private BigDecimal totalAmount;
    private String status;
    private String notes;
    private String createdBy;
    private LocalDateTime lastUpdated;

    // Constructors
    public PurchaseOrder() {}

    public PurchaseOrder(String poNumber, int supplierId, LocalDate expectedDeliveryDate) {
        this.poNumber = poNumber;
        this.supplierId = supplierId;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.status = "DRAFT";
        this.orderDate = LocalDateTime.now();
        this.totalAmount = BigDecimal.ZERO;
    }

    // Getters and Setters
    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
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
        return String.format("PurchaseOrder{id=%d, poNumber='%s', supplier='%s', status='%s', totalAmount=%.2f}",
                poId, poNumber, supplierName, status, totalAmount);
    }
}
