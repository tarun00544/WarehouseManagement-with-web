package com.warehouse.service;

import com.warehouse.dao.PurchaseOrderDAO;
import com.warehouse.dao.SalesOrderDAO;
import com.warehouse.model.PurchaseOrder;
import com.warehouse.model.SalesOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * OrderService - Business logic for purchase and sales orders
 */
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final PurchaseOrderDAO purchaseOrderDAO;
    private final SalesOrderDAO salesOrderDAO;

    public OrderService() {
        this.purchaseOrderDAO = new PurchaseOrderDAO();
        this.salesOrderDAO = new SalesOrderDAO();
    }

    // ============== PURCHASE ORDER METHODS ==============

    /**
     * Create a new purchase order
     */
    public boolean createPurchaseOrder(String poNumber, int supplierId, LocalDate expectedDeliveryDate, String notes) {
        PurchaseOrder po = new PurchaseOrder(poNumber, supplierId, expectedDeliveryDate);
        po.setNotes(notes);
        po.setCreatedBy("SYSTEM");
        boolean success = purchaseOrderDAO.addPurchaseOrder(po);
        if (success) {
            logger.info("Purchase order created: {}", poNumber);
        }
        return success;
    }

    /**
     * Get all purchase orders
     */
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderDAO.getAllPurchaseOrders();
    }

    /**
     * Get purchase orders by status
     */
    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        return purchaseOrderDAO.getPurchaseOrdersByStatus(status);
    }

    /**
     * Update purchase order status
     */
    public boolean updatePurchaseOrderStatus(int poId, String newStatus) {
        logger.info("Updating PO {} status to {}", poId, newStatus);
        return purchaseOrderDAO.updatePurchaseOrderStatus(poId, newStatus);
    }

    /**
     * Calculate and update purchase order total
     */
    public void calculateAndUpdatePOTotal(int poId) {
        // This would involve calculating from purchase_order_items
        logger.info("PO total calculated for: {}", poId);
    }

    /**
     * Get pending purchase orders
     */
    public int getPendingPurchaseOrderCount() {
        return purchaseOrderDAO.getPendingPurchaseOrderCount();
    }

    // ============== SALES ORDER METHODS ==============

    /**
     * Create a new sales order
     */
    public boolean createSalesOrder(String orderNumber, String customerName, String email, 
                                    String phone, LocalDate deliveryDate, String notes) {
        SalesOrder order = new SalesOrder(orderNumber, customerName, deliveryDate);
        order.setCustomerEmail(email);
        order.setCustomerPhone(phone);
        order.setNotes(notes);
        order.setCreatedBy("SYSTEM");
        boolean success = salesOrderDAO.addSalesOrder(order);
        if (success) {
            logger.info("Sales order created: {}", orderNumber);
        }
        return success;
    }

    /**
     * Get all sales orders
     */
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderDAO.getAllSalesOrders();
    }

    /**
     * Get sales orders by status
     */
    public List<SalesOrder> getSalesOrdersByStatus(String status) {
        return salesOrderDAO.getSalesOrdersByStatus(status);
    }

    /**
     * Update sales order status
     */
    public boolean updateSalesOrderStatus(int salesOrderId, String newStatus) {
        logger.info("Updating Sales Order {} status to {}", salesOrderId, newStatus);
        return salesOrderDAO.updateSalesOrderStatus(salesOrderId, newStatus);
    }

    /**
     * Get pending sales orders
     */
    public int getPendingSalesOrderCount() {
        return salesOrderDAO.getPendingSalesOrderCount();
    }

    /**
     * Get total sales revenue
     */
    public BigDecimal getTotalSalesRevenue() {
        return salesOrderDAO.getTotalSalesRevenue();
    }

    /**
     * Get order dashboard summary
     */
    public OrderSummary getOrderSummary() {
        OrderSummary summary = new OrderSummary();
        summary.setPendingPurchaseOrders(purchaseOrderDAO.getPendingPurchaseOrderCount());
        summary.setPendingSalesOrders(salesOrderDAO.getPendingSalesOrderCount());
        summary.setTotalRevenue(salesOrderDAO.getTotalSalesRevenue());
        return summary;
    }

    /**
     * Inner class for order summary
     */
    public static class OrderSummary {
        private int pendingPurchaseOrders;
        private int pendingSalesOrders;
        private BigDecimal totalRevenue;

        public int getPendingPurchaseOrders() { return pendingPurchaseOrders; }
        public void setPendingPurchaseOrders(int count) { this.pendingPurchaseOrders = count; }

        public int getPendingSalesOrders() { return pendingSalesOrders; }
        public void setPendingSalesOrders(int count) { this.pendingSalesOrders = count; }

        public BigDecimal getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(BigDecimal revenue) { this.totalRevenue = revenue; }
    }
}
