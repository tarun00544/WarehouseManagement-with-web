package com.warehouse.dao;

import com.warehouse.model.PurchaseOrder;
import com.warehouse.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PurchaseOrderDAO - Data Access Object for PurchaseOrder operations
 */
public class PurchaseOrderDAO {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderDAO.class);

    public boolean addPurchaseOrder(PurchaseOrder po) {
        String sql = "INSERT INTO purchase_orders (po_number, supplier_id, expected_delivery_date, " +
                     "status, notes, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, po.getPoNumber());
            stmt.setInt(2, po.getSupplierId());
            stmt.setDate(3, java.sql.Date.valueOf(po.getExpectedDeliveryDate()));
            stmt.setString(4, po.getStatus() != null ? po.getStatus() : "DRAFT");
            stmt.setString(5, po.getNotes());
            stmt.setString(6, po.getCreatedBy());
            logger.info("Purchase order created: {}", po.getPoNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error adding purchase order", e);
            return false;
        }
    }

    public PurchaseOrder getPurchaseOrderById(int poId) {
        String sql = "SELECT po.*, s.supplier_name FROM purchase_orders po " +
                     "LEFT JOIN suppliers s ON po.supplier_id = s.supplier_id WHERE po.po_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, poId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPurchaseOrder(rs);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving PO by ID", e);
        }
        return null;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        List<PurchaseOrder> orders = new ArrayList<>();
        String sql = "SELECT po.*, s.supplier_name FROM purchase_orders po " +
                     "LEFT JOIN suppliers s ON po.supplier_id = s.supplier_id " +
                     "ORDER BY po.order_date DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(mapResultSetToPurchaseOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all POs", e);
        }
        return orders;
    }

    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        List<PurchaseOrder> orders = new ArrayList<>();
        String sql = "SELECT po.*, s.supplier_name FROM purchase_orders po " +
                     "LEFT JOIN suppliers s ON po.supplier_id = s.supplier_id " +
                     "WHERE po.status = ? ORDER BY po.order_date DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(mapResultSetToPurchaseOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving POs by status", e);
        }
        return orders;
    }

    public boolean updatePurchaseOrderStatus(int poId, String status) {
        String sql = "UPDATE purchase_orders SET status = ? WHERE po_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, poId);
            logger.info("PO status updated: {} to {}", poId, status);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating PO status", e);
            return false;
        }
    }

    public boolean updatePurchaseOrderTotal(int poId, java.math.BigDecimal total) {
        String sql = "UPDATE purchase_orders SET total_amount = ? WHERE po_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, total);
            stmt.setInt(2, poId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating PO total", e);
            return false;
        }
    }

    public int getPendingPurchaseOrderCount() {
        String sql = "SELECT COUNT(*) as count FROM purchase_orders " +
                     "WHERE status IN ('DRAFT', 'SUBMITTED', 'CONFIRMED')";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("Error getting pending PO count", e);
        }
        return 0;
    }

    private PurchaseOrder mapResultSetToPurchaseOrder(ResultSet rs) throws SQLException {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoId(rs.getInt("po_id"));
        po.setPoNumber(rs.getString("po_number"));
        po.setSupplierId(rs.getInt("supplier_id"));
        po.setSupplierName(rs.getString("supplier_name"));
        po.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        po.setExpectedDeliveryDate(rs.getDate("expected_delivery_date").toLocalDate());
        po.setActualDeliveryDate(rs.getDate("actual_delivery_date") != null ? 
                                rs.getDate("actual_delivery_date").toLocalDate() : null);
        po.setTotalAmount(rs.getBigDecimal("total_amount"));
        po.setStatus(rs.getString("status"));
        po.setNotes(rs.getString("notes"));
        po.setCreatedBy(rs.getString("created_by"));
        po.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        return po;
    }
}
