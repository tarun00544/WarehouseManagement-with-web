package com.warehouse.model;

import java.time.LocalDateTime;

/**
 * InventoryAlert - Model class representing inventory alerts
 */
public class InventoryAlert {
    private int alertId;
    private int productId;
    private String productName;
    private String alertType;
    private String alertMessage;
    private int currentStock;
    private int thresholdValue;
    private String severity;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime resolvedDate;
    private String resolvedBy;

    // Constructors
    public InventoryAlert() {}

    public InventoryAlert(int productId, String alertType, String alertMessage,
                         int currentStock, int thresholdValue, String severity) {
        this.productId = productId;
        this.alertType = alertType;
        this.alertMessage = alertMessage;
        this.currentStock = currentStock;
        this.thresholdValue = thresholdValue;
        this.severity = severity;
        this.status = "ACTIVE";
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(int thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    @Override
    public String toString() {
        return String.format("InventoryAlert{id=%d, product='%s', type='%s', severity='%s', status='%s'}",
                alertId, productName, alertType, severity, status);
    }
}
