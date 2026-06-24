package com.warehouse.dao;

import com.warehouse.model.Supplier;
import com.warehouse.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SupplierDAO - Data Access Object for Supplier operations
 */
public class SupplierDAO {
    private static final Logger logger = LoggerFactory.getLogger(SupplierDAO.class);

    public boolean addSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (supplier_name, contact_person, email, phone, " +
                     "address, city, country, payment_terms, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getEmail());
            stmt.setString(4, supplier.getPhone());
            stmt.setString(5, supplier.getAddress());
            stmt.setString(6, supplier.getCity());
            stmt.setString(7, supplier.getCountry());
            stmt.setString(8, supplier.getPaymentTerms());
            stmt.setString(9, supplier.getStatus() != null ? supplier.getStatus() : "ACTIVE");
            logger.info("Supplier added: {}", supplier.getSupplierName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error adding supplier", e);
            return false;
        }
    }

    public Supplier getSupplierById(int supplierId) {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSupplier(rs);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving supplier by ID", e);
        }
        return null;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers WHERE status = 'ACTIVE' ORDER BY supplier_name";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving suppliers", e);
        }
        return suppliers;
    }

    public boolean updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET supplier_name = ?, contact_person = ?, " +
                     "email = ?, phone = ?, address = ?, city = ?, country = ?, " +
                     "payment_terms = ?, status = ? WHERE supplier_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getEmail());
            stmt.setString(4, supplier.getPhone());
            stmt.setString(5, supplier.getAddress());
            stmt.setString(6, supplier.getCity());
            stmt.setString(7, supplier.getCountry());
            stmt.setString(8, supplier.getPaymentTerms());
            stmt.setString(9, supplier.getStatus());
            stmt.setInt(10, supplier.getSupplierId());
            logger.info("Supplier updated: {}", supplier.getSupplierId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating supplier", e);
            return false;
        }
    }

    public boolean deactivateSupplier(int supplierId) {
        String sql = "UPDATE suppliers SET status = 'INACTIVE' WHERE supplier_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deactivating supplier", e);
            return false;
        }
    }

    public int getTotalSupplierCount() {
        String sql = "SELECT COUNT(*) as total FROM suppliers WHERE status = 'ACTIVE'";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            logger.error("Error getting supplier count", e);
        }
        return 0;
    }

    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(rs.getInt("supplier_id"));
        supplier.setSupplierName(rs.getString("supplier_name"));
        supplier.setContactPerson(rs.getString("contact_person"));
        supplier.setEmail(rs.getString("email"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setAddress(rs.getString("address"));
        supplier.setCity(rs.getString("city"));
        supplier.setCountry(rs.getString("country"));
        supplier.setPaymentTerms(rs.getString("payment_terms"));
        supplier.setStatus(rs.getString("status"));
        supplier.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        supplier.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        return supplier;
    }
}
