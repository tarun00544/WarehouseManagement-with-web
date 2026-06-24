package com.warehouse.dao;

import com.warehouse.model.SalesOrder;
import com.warehouse.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SalesOrderDAO - Data Access Object for SalesOrder operations
 */
public class SalesOrderDAO {
    private static final Logger logger = LoggerFactory.getLogger(SalesOrderDAO.class);

    public boolean addSalesOrder(SalesOrder order) {
        String sql = "INSERT INTO sales_orders (order_number, customer_name, customer_email, " +
                     "customer_phone, delivery_date, status, notes, created_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getOrderNumber());
            stmt.setString(2, order.getCustomerName());
            stmt.setString(3, order.getCustomerEmail());
            stmt.setString(4, order.getCustomerPhone());
            stmt.setDate(5, java.sql.Date.valueOf(order.getDeliveryDate()));
            stmt.setString(6, order.getStatus() != null ? order.getStatus() : "PENDING");
            stmt.setString(7, order.getNotes());
            stmt.setString(8, order.getCreatedBy());
            logger.info("Sales order created: {}", order.getOrderNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error adding sales order", e);
            return false;
        }
    }

    public SalesOrder getSalesOrderById(int salesOrderId) {
        String sql = "SELECT * FROM sales_orders WHERE sales_order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salesOrderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSalesOrder(rs);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving sales order by ID", e);
        }
        return null;
    }

    public List<SalesOrder> getAllSalesOrders() {
        List<SalesOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM sales_orders ORDER BY order_date DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(mapResultSetToSalesOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all sales orders", e);
        }
        return orders;
    }

    public List<SalesOrder> getSalesOrdersByStatus(String status) {
        List<SalesOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM sales_orders WHERE status = ? ORDER BY order_date DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(mapResultSetToSalesOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving sales orders by status", e);
        }
        return orders;
    }

    public boolean updateSalesOrderStatus(int salesOrderId, String status) {
        String sql = "UPDATE sales_orders SET status = ? WHERE sales_order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, salesOrderId);
            logger.info("Sales order status updated: {} to {}", salesOrderId, status);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating sales order status", e);
            return false;
        }
    }

    public boolean updateSalesOrderTotal(int salesOrderId, java.math.BigDecimal total) {
        String sql = "UPDATE sales_orders SET total_amount = ? WHERE sales_order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, total);
            stmt.setInt(2, salesOrderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating sales order total", e);
            return false;
        }
    }

    public int getPendingSalesOrderCount() {
        String sql = "SELECT COUNT(*) as count FROM sales_orders WHERE status IN ('PENDING', 'PROCESSING')";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("Error getting pending sales order count", e);
        }
        return 0;
    }

    public java.math.BigDecimal getTotalSalesRevenue() {
        String sql = "SELECT SUM(total_amount) as revenue FROM sales_orders WHERE status != 'CANCELLED'";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getBigDecimal("revenue");
            }
        } catch (SQLException e) {
            logger.error("Error calculating total sales revenue", e);
        }
        return java.math.BigDecimal.ZERO;
    }

    private SalesOrder mapResultSetToSalesOrder(ResultSet rs) throws SQLException {
        SalesOrder order = new SalesOrder();
        order.setSalesOrderId(rs.getInt("sales_order_id"));
        order.setOrderNumber(rs.getString("order_number"));
        order.setCustomerName(rs.getString("customer_name"));
        order.setCustomerEmail(rs.getString("customer_email"));
        order.setCustomerPhone(rs.getString("customer_phone"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        order.setDeliveryDate(rs.getDate("delivery_date") != null ? 
                             rs.getDate("delivery_date").toLocalDate() : null);
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setStatus(rs.getString("status"));
        order.setNotes(rs.getString("notes"));
        order.setCreatedBy(rs.getString("created_by"));
        order.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        return order;
    }
}
