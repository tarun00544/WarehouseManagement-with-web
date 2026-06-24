package com.warehouse.service;

import com.warehouse.dao.*;
import com.warehouse.model.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * ReportService - Business logic for warehouse reports and analytics
 */
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final ProductDAO productDAO;
    private final StockDAO stockDAO;
    private final InventoryAlertDAO alertDAO;
    private final PurchaseOrderDAO poDAO;
    private final SalesOrderDAO soDAO;
    private final SupplierDAO supplierDAO;
    private final Gson gson;

    public ReportService() {
        this.productDAO = new ProductDAO();
        this.stockDAO = new StockDAO();
        this.alertDAO = new InventoryAlertDAO();
        this.poDAO = new PurchaseOrderDAO();
        this.soDAO = new SalesOrderDAO();
        this.supplierDAO = new SupplierDAO();
        this.gson = new Gson();
    }

    /**
     * Generate inventory status report
     */
    public InventoryReport generateInventoryReport() {
        InventoryReport report = new InventoryReport();
        report.setGeneratedAt(LocalDateTime.now());
        report.setTotalProducts(productDAO.getTotalProductCount());
        report.setTotalQuantity(stockDAO.getTotalInventoryQuantity());
        report.setTotalValue(stockDAO.getTotalInventoryValue());
        report.setLowStockItems(stockDAO.getLowStockItems(Integer.MAX_VALUE).size());
        report.setOverstockItems(stockDAO.getOverstockItems().size());
        report.setActiveAlerts(alertDAO.getActiveAlertCount());
        logger.info("Inventory report generated");
        return report;
    }

    /**
     * Generate product analysis report
     */
    public String generateProductAnalysisReport() {
        List<Product> products = productDAO.getAllProducts();
        StringBuilder report = new StringBuilder();
        report.append("=== PRODUCT ANALYSIS REPORT ===\n");
        report.append("Generated: ").append(LocalDateTime.now()).append("\n\n");
        report.append(String.format("%-10s %-30s %-15s %-15s\n", "ID", "Product Name", "SKU", "Category"));
        report.append("=".repeat(70)).append("\n");

        for (Product p : products) {
            report.append(String.format("%-10d %-30s %-15s %-15s\n", 
                    p.getProductId(), 
                    truncate(p.getProductName(), 28),
                    p.getSku(),
                    p.getCategoryName()));
        }
        return report.toString();
    }

    /**
     * Generate stock movement report
     */
    public String generateStockMovementReport() {
        List<Stock> stocks = stockDAO.getAllStock();
        StringBuilder report = new StringBuilder();
        report.append("=== STOCK MOVEMENT REPORT ===\n");
        report.append("Generated: ").append(LocalDateTime.now()).append("\n\n");
        report.append(String.format("%-15s %-30s %-12s %-12s %-12s\n", 
                "SKU", "Product", "Current", "Reserved", "Available"));
        report.append("=".repeat(80)).append("\n");

        for (Stock s : stocks) {
            report.append(String.format("%-15s %-30s %-12d %-12d %-12d\n",
                    s.getSku(),
                    truncate(s.getProductName(), 28),
                    s.getCurrentQuantity(),
                    s.getReservedQuantity(),
                    s.getAvailableQuantity()));
        }
        return report.toString();
    }

    /**
     * Generate alerts summary report
     */
    public String generateAlertsSummaryReport() {
        List<InventoryAlert> alerts = alertDAO.getActiveAlerts();
        StringBuilder report = new StringBuilder();
        report.append("=== ACTIVE ALERTS REPORT ===\n");
        report.append("Generated: ").append(LocalDateTime.now()).append("\n");
        report.append("Total Active Alerts: ").append(alerts.size()).append("\n\n");
        report.append(String.format("%-10s %-30s %-15s %-12s %-12s\n", 
                "Alert ID", "Product", "Type", "Severity", "Status"));
        report.append("=".repeat(80)).append("\n");

        for (InventoryAlert a : alerts) {
            report.append(String.format("%-10d %-30s %-15s %-12s %-12s\n",
                    a.getAlertId(),
                    truncate(a.getProductName(), 28),
                    a.getAlertType(),
                    a.getSeverity(),
                    a.getStatus()));
        }
        return report.toString();
    }

    /**
     * Generate supplier performance report
     */
    public String generateSupplierPerformanceReport() {
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();
        StringBuilder report = new StringBuilder();
        report.append("=== SUPPLIER PERFORMANCE REPORT ===\n");
        report.append("Generated: ").append(LocalDateTime.now()).append("\n\n");
        report.append(String.format("%-10s %-30s %-20s %-15s\n", 
                "ID", "Supplier Name", "Contact", "Status"));
        report.append("=".repeat(75)).append("\n");

        for (Supplier s : suppliers) {
            report.append(String.format("%-10d %-30s %-20s %-15s\n",
                    s.getSupplierId(),
                    truncate(s.getSupplierName(), 28),
                    truncate(s.getContactPerson(), 18),
                    s.getStatus()));
        }
        return report.toString();
    }

    /**
     * Generate purchase order analytics
     */
    public PurchaseOrderAnalytics generatePurchaseOrderAnalytics() {
        PurchaseOrderAnalytics analytics = new PurchaseOrderAnalytics();
        analytics.setGeneratedAt(LocalDateTime.now());
        analytics.setDraftOrders(poDAO.getPurchaseOrdersByStatus("DRAFT").size());
        analytics.setSubmittedOrders(poDAO.getPurchaseOrdersByStatus("SUBMITTED").size());
        analytics.setDeliveredOrders(poDAO.getPurchaseOrdersByStatus("DELIVERED").size());
        analytics.setTotalPendingOrders(poDAO.getPendingPurchaseOrderCount());
        logger.info("Purchase order analytics generated");
        return analytics;
    }

    /**
     * Generate sales order analytics
     */
    public SalesOrderAnalytics generateSalesOrderAnalytics() {
        SalesOrderAnalytics analytics = new SalesOrderAnalytics();
        analytics.setGeneratedAt(LocalDateTime.now());
        analytics.setPendingOrders(soDAO.getSalesOrdersByStatus("PENDING").size());
        analytics.setProcessingOrders(soDAO.getSalesOrdersByStatus("PROCESSING").size());
        analytics.setShippedOrders(soDAO.getSalesOrdersByStatus("SHIPPED").size());
        analytics.setDeliveredOrders(soDAO.getSalesOrdersByStatus("DELIVERED").size());
        analytics.setTotalRevenue(soDAO.getTotalSalesRevenue());
        logger.info("Sales order analytics generated");
        return analytics;
    }

    /**
     * Helper method to truncate strings
     */
    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length) : str;
    }

    // ============== REPORT CLASSES ==============

    public static class InventoryReport {
        private LocalDateTime generatedAt;
        private int totalProducts;
        private int totalQuantity;
        private int totalValue;
        private int lowStockItems;
        private int overstockItems;
        private int activeAlerts;

        public LocalDateTime getGeneratedAt() { return generatedAt; }
        public void setGeneratedAt(LocalDateTime dt) { this.generatedAt = dt; }
        public int getTotalProducts() { return totalProducts; }
        public void setTotalProducts(int count) { this.totalProducts = count; }
        public int getTotalQuantity() { return totalQuantity; }
        public void setTotalQuantity(int qty) { this.totalQuantity = qty; }
        public int getTotalValue() { return totalValue; }
        public void setTotalValue(int val) { this.totalValue = val; }
        public int getLowStockItems() { return lowStockItems; }
        public void setLowStockItems(int count) { this.lowStockItems = count; }
        public int getOverstockItems() { return overstockItems; }
        public void setOverstockItems(int count) { this.overstockItems = count; }
        public int getActiveAlerts() { return activeAlerts; }
        public void setActiveAlerts(int count) { this.activeAlerts = count; }
    }

    public static class PurchaseOrderAnalytics {
        private LocalDateTime generatedAt;
        private int draftOrders;
        private int submittedOrders;
        private int deliveredOrders;
        private int totalPendingOrders;

        public LocalDateTime getGeneratedAt() { return generatedAt; }
        public void setGeneratedAt(LocalDateTime dt) { this.generatedAt = dt; }
        public int getDraftOrders() { return draftOrders; }
        public void setDraftOrders(int count) { this.draftOrders = count; }
        public int getSubmittedOrders() { return submittedOrders; }
        public void setSubmittedOrders(int count) { this.submittedOrders = count; }
        public int getDeliveredOrders() { return deliveredOrders; }
        public void setDeliveredOrders(int count) { this.deliveredOrders = count; }
        public int getTotalPendingOrders() { return totalPendingOrders; }
        public void setTotalPendingOrders(int count) { this.totalPendingOrders = count; }
    }

    public static class SalesOrderAnalytics {
        private LocalDateTime generatedAt;
        private int pendingOrders;
        private int processingOrders;
        private int shippedOrders;
        private int deliveredOrders;
        private java.math.BigDecimal totalRevenue;

        public LocalDateTime getGeneratedAt() { return generatedAt; }
        public void setGeneratedAt(LocalDateTime dt) { this.generatedAt = dt; }
        public int getPendingOrders() { return pendingOrders; }
        public void setPendingOrders(int count) { this.pendingOrders = count; }
        public int getProcessingOrders() { return processingOrders; }
        public void setProcessingOrders(int count) { this.processingOrders = count; }
        public int getShippedOrders() { return shippedOrders; }
        public void setShippedOrders(int count) { this.shippedOrders = count; }
        public int getDeliveredOrders() { return deliveredOrders; }
        public void setDeliveredOrders(int count) { this.deliveredOrders = count; }
        public java.math.BigDecimal getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(java.math.BigDecimal revenue) { this.totalRevenue = revenue; }
    }
}
