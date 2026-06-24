package com.warehouse.dao;

import com.warehouse.model.Stock;
import com.warehouse.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StockDAO - Data Access Object for Stock operations
 */
public class StockDAO {
    private static final Logger logger = LoggerFactory.getLogger(StockDAO.class);

    public Stock getStockByProductId(int productId) {
        String sql = "SELECT s.*, p.product_name, p.sku, w.location_name FROM stock_inventory s " +
                     "LEFT JOIN products p ON s.product_id = p.product_id " +
                     "LEFT JOIN warehouse_locations w ON s.location_id = w.location_id " +
                     "WHERE s.product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToStock(rs);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving stock", e);
        }
        return null;
    }

    public List<Stock> getAllStock() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT s.*, p.product_name, p.sku, w.location_name FROM stock_inventory s " +
                     "LEFT JOIN products p ON s.product_id = p.product_id " +
                     "LEFT JOIN warehouse_locations w ON s.location_id = w.location_id " +
                     "ORDER BY p.product_name";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stocks.add(mapResultSetToStock(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all stock", e);
        }
        return stocks;
    }

    public boolean updateStockQuantity(int productId, int quantity, String movementType) {
        String sql = "UPDATE stock_inventory SET current_quantity = current_quantity + ?, " +
                     "last_stock_check = NOW() WHERE product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int change = movementType.equals("INBOUND") ? quantity : -quantity;
            stmt.setInt(1, change);
            stmt.setInt(2, productId);
            logger.info("Stock updated for product {}: {}", productId, change);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating stock quantity", e);
            return false;
        }
    }

    public boolean updateReservedQuantity(int productId, int quantity) {
        String sql = "UPDATE stock_inventory SET reserved_quantity = reserved_quantity + ? " +
                     "WHERE product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating reserved quantity", e);
            return false;
        }
    }

    public List<Stock> getLowStockItems(int threshold) {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT s.*, p.product_name, p.sku, p.reorder_level, w.location_name " +
                     "FROM stock_inventory s " +
                     "LEFT JOIN products p ON s.product_id = p.product_id " +
                     "LEFT JOIN warehouse_locations w ON s.location_id = w.location_id " +
                     "WHERE s.available_quantity <= ? AND p.status = 'ACTIVE' " +
                     "ORDER BY s.available_quantity ASC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, threshold);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stocks.add(mapResultSetToStock(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving low stock items", e);
        }
        return stocks;
    }

    public List<Stock> getOverstockItems() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT s.*, p.product_name, p.sku, w.location_name FROM stock_inventory s " +
                     "LEFT JOIN products p ON s.product_id = p.product_id " +
                     "LEFT JOIN warehouse_locations w ON s.location_id = w.location_id " +
                     "WHERE s.current_quantity > (p.reorder_level * 5) AND p.status = 'ACTIVE' " +
                     "ORDER BY s.current_quantity DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stocks.add(mapResultSetToStock(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving overstock items", e);
        }
        return stocks;
    }

    public int getTotalInventoryValue() {
        String sql = "SELECT SUM(s.current_quantity * p.unit_price) as total_value " +
                     "FROM stock_inventory s " +
                     "JOIN products p ON s.product_id = p.product_id";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total_value");
            }
        } catch (SQLException e) {
            logger.error("Error calculating inventory value", e);
        }
        return 0;
    }

    public int getTotalInventoryQuantity() {
        String sql = "SELECT SUM(current_quantity) as total_qty FROM stock_inventory";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total_qty");
            }
        } catch (SQLException e) {
            logger.error("Error getting total inventory quantity", e);
        }
        return 0;
    }

    private Stock mapResultSetToStock(ResultSet rs) throws SQLException {
        Stock stock = new Stock();
        stock.setStockId(rs.getInt("stock_id"));
        stock.setProductId(rs.getInt("product_id"));
        stock.setProductName(rs.getString("product_name"));
        stock.setSku(rs.getString("sku"));
        stock.setCurrentQuantity(rs.getInt("current_quantity"));
        stock.setReservedQuantity(rs.getInt("reserved_quantity"));
        stock.setAvailableQuantity(rs.getInt("available_quantity"));
        stock.setLocationId(rs.getInt("location_id"));
        stock.setLocationName(rs.getString("location_name"));
        stock.setLastStockCheck(rs.getTimestamp("last_stock_check") != null ? 
                               rs.getTimestamp("last_stock_check").toLocalDateTime() : null);
        stock.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        stock.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        return stock;
    }
}
