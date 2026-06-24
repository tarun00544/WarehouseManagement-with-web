package com.warehouse.service;

import com.warehouse.dao.ProductDAO;
import com.warehouse.dao.StockDAO;
import com.warehouse.dao.InventoryAlertDAO;
import com.warehouse.model.Product;
import com.warehouse.model.Stock;
import com.warehouse.model.InventoryAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * InventoryService - Business logic for inventory management
 */
public class InventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    private final ProductDAO productDAO;
    private final StockDAO stockDAO;
    private final InventoryAlertDAO alertDAO;

    public InventoryService() {
        this.productDAO = new ProductDAO();
        this.stockDAO = new StockDAO();
        this.alertDAO = new InventoryAlertDAO();
    }

    /**
     * Add new product with initial stock
     */
    public boolean addProductWithStock(Product product, int initialStock) {
        if (productDAO.addProduct(product)) {
            logger.info("Product added successfully");
            return true;
        }
        return false;
    }

    /**
     * Update stock quantity and check for alerts
     */
    public void updateStock(int productId, int quantity, String movementType) {
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            logger.warn("Product not found: {}", productId);
            return;
        }

        // Update stock quantity
        stockDAO.updateStockQuantity(productId, quantity, movementType);

        // Check and create alerts
        checkAndCreateAlerts(productId);
        logger.info("Stock updated for product {}: {} units ({})", productId, quantity, movementType);
    }

    /**
     * Check inventory and create alerts
     */
    public void checkAndCreateAlerts(int productId) {
        Product product = productDAO.getProductById(productId);
        Stock stock = stockDAO.getStockByProductId(productId);

        if (product != null && stock != null) {
            // Check for low stock
            if (stock.getAvailableQuantity() <= product.getReorderLevel()) {
                createLowStockAlert(product, stock);
            }

            // Check for overstock
            if (stock.getCurrentQuantity() > (product.getReorderLevel() * 5)) {
                createOverstockAlert(product, stock);
            }
        }
    }

    /**
     * Create low stock alert
     */
    private void createLowStockAlert(Product product, Stock stock) {
        InventoryAlert alert = new InventoryAlert(
            product.getProductId(),
            "LOW_STOCK",
            "Stock for " + product.getProductName() + " is running low",
            stock.getAvailableQuantity(),
            product.getReorderLevel(),
            stock.getAvailableQuantity() < 5 ? "CRITICAL" : "HIGH"
        );
        alertDAO.addAlert(alert);
        logger.warn("Low stock alert created for product: {}", product.getProductName());
    }

    /**
     * Create overstock alert
     */
    private void createOverstockAlert(Product product, Stock stock) {
        InventoryAlert alert = new InventoryAlert(
            product.getProductId(),
            "OVERSTOCK",
            "Stock for " + product.getProductName() + " exceeds recommended level",
            stock.getCurrentQuantity(),
            product.getReorderLevel() * 5,
            "MEDIUM"
        );
        alertDAO.addAlert(alert);
        logger.info("Overstock alert created for product: {}", product.getProductName());
    }

    /**
     * Get all products that need reordering
     */
    public List<Stock> getLowStockItems() {
        return stockDAO.getLowStockItems(Integer.MAX_VALUE);
    }

    /**
     * Get all overstock items
     */
    public List<Stock> getOverstockItems() {
        return stockDAO.getOverstockItems();
    }

    /**
     * Get active alerts
     */
    public List<InventoryAlert> getActiveAlerts() {
        return alertDAO.getActiveAlerts();
    }

    /**
     * Resolve inventory alert
     */
    public boolean resolveAlert(int alertId, String resolvedBy) {
        return alertDAO.resolveAlert(alertId, resolvedBy);
    }

    /**
     * Get inventory summary
     */
    public InventorySummary getInventorySummary() {
        InventorySummary summary = new InventorySummary();
        summary.setTotalProducts(productDAO.getTotalProductCount());
        summary.setTotalQuantity(stockDAO.getTotalInventoryQuantity());
        summary.setTotalInventoryValue(stockDAO.getTotalInventoryValue());
        summary.setActiveAlerts(alertDAO.getActiveAlertCount());
        summary.setCriticalAlerts(alertDAO.getCriticalAlertCount());
        return summary;
    }

    /**
     * Inner class for inventory summary
     */
    public static class InventorySummary {
        private int totalProducts;
        private int totalQuantity;
        private int totalInventoryValue;
        private int activeAlerts;
        private int criticalAlerts;

        public int getTotalProducts() { return totalProducts; }
        public void setTotalProducts(int totalProducts) { this.totalProducts = totalProducts; }

        public int getTotalQuantity() { return totalQuantity; }
        public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

        public int getTotalInventoryValue() { return totalInventoryValue; }
        public void setTotalInventoryValue(int totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; }

        public int getActiveAlerts() { return activeAlerts; }
        public void setActiveAlerts(int activeAlerts) { this.activeAlerts = activeAlerts; }

        public int getCriticalAlerts() { return criticalAlerts; }
        public void setCriticalAlerts(int criticalAlerts) { this.criticalAlerts = criticalAlerts; }
    }
}
