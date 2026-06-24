package com.warehouse.dao;

import com.warehouse.model.InventoryAlert;
import com.warehouse.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * InventoryAlertDAO - Data Access Object for InventoryAlert operations
 */
public class InventoryAlertDAO {
    private static final Logger logger = LoggerFactory.getLogger(InventoryAlertDAO.class);

    public boolean addAlert(InventoryAlert alert) {
        String sql = "INSERT INTO inventory_alerts (product_id, alert_type, alert_message, " +
                     "current_stock, threshold_value, severity, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alert.getProductId());
            stmt.setString(2, alert.getAlertType());
            stmt.setString(3, alert.getAlertMessage());
            stmt.setInt(4, alert.getCurrentStock());
            stmt.setInt(5, alert.getThresholdValue());
            stmt.setString(6, alert.getSeverity());
            stmt.setString(7, "ACTIVE");
            logger.info("Alert created for product {}: {}", alert.getProductId(), alert.getAlertType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error adding inventory alert", e);
            return false;
        }
    }

    public List<InventoryAlert> getActiveAlerts() {
        List<InventoryAlert> alerts = new ArrayList<>();
        String sql = "SELECT ia.*, p.product_name FROM inventory_alerts ia " +
                     "LEFT JOIN products p ON ia.product_id = p.product_id " +
                     "WHERE ia.status = 'ACTIVE' ORDER BY ia.severity DESC, ia.created_date DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                alerts.add(mapResultSetToAlert(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving active alerts", e);
        }
        return alerts;
    }

    public List<InventoryAlert> getAlertsByType(String alertType) {
        List<InventoryAlert> alerts = new ArrayList<>();
        String sql = "SELECT ia.*, p.product_name FROM inventory_alerts ia " +
                     "LEFT JOIN products p ON ia.product_id = p.product_id " +
                     "WHERE ia.alert_type = ? AND ia.status = 'ACTIVE'";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alertType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alerts.add(mapResultSetToAlert(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving alerts by type", e);
        }
        return alerts;
    }

    public List<InventoryAlert> getAlertsBySeverity(String severity) {
        List<InventoryAlert> alerts = new ArrayList<>();
        String sql = "SELECT ia.*, p.product_name FROM inventory_alerts ia " +
                     "LEFT JOIN products p ON ia.product_id = p.product_id " +
                     "WHERE ia.severity = ? AND ia.status = 'ACTIVE' " +
                     "ORDER BY ia.created_date DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, severity);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alerts.add(mapResultSetToAlert(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving alerts by severity", e);
        }
        return alerts;
    }

    public boolean resolveAlert(int alertId, String resolvedBy) {
        String sql = "UPDATE inventory_alerts SET status = 'RESOLVED', " +
                     "resolved_date = NOW(), resolved_by = ? WHERE alert_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, resolvedBy);
            stmt.setInt(2, alertId);
            logger.info("Alert resolved: {}", alertId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error resolving alert", e);
            return false;
        }
    }

    public int getActiveAlertCount() {
        String sql = "SELECT COUNT(*) as count FROM inventory_alerts WHERE status = 'ACTIVE'";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("Error getting active alert count", e);
        }
        return 0;
    }

    public int getCriticalAlertCount() {
        String sql = "SELECT COUNT(*) as count FROM inventory_alerts " +
                     "WHERE status = 'ACTIVE' AND severity = 'CRITICAL'";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("Error getting critical alert count", e);
        }
        return 0;
    }

    private InventoryAlert mapResultSetToAlert(ResultSet rs) throws SQLException {
        InventoryAlert alert = new InventoryAlert();
        alert.setAlertId(rs.getInt("alert_id"));
        alert.setProductId(rs.getInt("product_id"));
        alert.setProductName(rs.getString("product_name"));
        alert.setAlertType(rs.getString("alert_type"));
        alert.setAlertMessage(rs.getString("alert_message"));
        alert.setCurrentStock(rs.getInt("current_stock"));
        alert.setThresholdValue(rs.getInt("threshold_value"));
        alert.setSeverity(rs.getString("severity"));
        alert.setStatus(rs.getString("status"));
        alert.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        alert.setResolvedDate(rs.getTimestamp("resolved_date") != null ? 
                             rs.getTimestamp("resolved_date").toLocalDateTime() : null);
        alert.setResolvedBy(rs.getString("resolved_by"));
        return alert;
    }
}
