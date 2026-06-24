package com.warehouse.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Product - Model class representing a product in the warehouse
 */
public class Product {
    private int productId;
    private String productName;
    private String sku;
    private int categoryId;
    private String categoryName;
    private String description;
    private BigDecimal unitPrice;
    private int reorderLevel;
    private int reorderQuantity;
    private int supplierId;
    private String supplierName;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;

    // Constructors
    public Product() {}

    public Product(int productId, String productName, String sku, String categoryName,
                   BigDecimal unitPrice, int reorderLevel) {
        this.productId = productId;
        this.productName = productName;
        this.sku = sku;
        this.categoryName = categoryName;
        this.unitPrice = unitPrice;
        this.reorderLevel = reorderLevel;
    }

    // Getters and Setters
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public int getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
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

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', sku='%s', price=%.2f, reorderLevel=%d}",
                productId, productName, sku, unitPrice, reorderLevel);
    }
}
