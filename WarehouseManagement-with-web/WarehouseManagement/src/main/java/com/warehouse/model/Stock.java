package com.warehouse.model;

import java.time.LocalDateTime;

/**
 * Stock - Model class representing stock inventory
 */
public class Stock {
    private int stockId;
    private int productId;
    private String productName;
    private String sku;
    private int currentQuantity;
    private int reservedQuantity;
    private int availableQuantity;
    private int locationId;
    private String locationName;
    private LocalDateTime lastStockCheck;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;

    // Constructors
    public Stock() {}

    public Stock(int productId, int currentQuantity, int reservedQuantity) {
        this.productId = productId;
        this.currentQuantity = currentQuantity;
        this.reservedQuantity = reservedQuantity;
        this.availableQuantity = currentQuantity - reservedQuantity;
    }

    // Getters and Setters
    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LocalDateTime getLastStockCheck() {
        return lastStockCheck;
    }

    public void setLastStockCheck(LocalDateTime lastStockCheck) {
        this.lastStockCheck = lastStockCheck;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return String.format("Stock{id=%d, product='%s', current=%d, reserved=%d, available=%d}",
                stockId, productName, currentQuantity, reservedQuantity, availableQuantity);
    }
}
