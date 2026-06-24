package com.warehouse.dao;

import com.warehouse.model.Product;
import com.warehouse.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDAO - Data Access Object for Product operations
 */
public class ProductDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    /**
     * Add a new product
     */
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (product_name, sku, category_id, description, " +
                     "unit_price, reorder_level, reorder_quantity, supplier_id, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getSku());
            stmt.setInt(3, product.getCategoryId());
            stmt.setString(4, product.getDescription());
            stmt.setBigDecimal(5, product.getUnitPrice());
            stmt.setInt(6, product.getReorderLevel());
            stmt.setInt(7, product.getReorderQuantity());
            stmt.setInt(8, product.getSupplierId());
            stmt.setString(9, "ACTIVE");

            int rowsInserted = stmt.executeUpdate();
            logger.info("Product added successfully: {}", product.getSku());
            return rowsInserted > 0;
        } catch (SQLException e) {
            logger.error("Error adding product", e);
            return false;
        }
    }

    /**
     * Get product by ID
     */
    public Product getProductById(int productId) {
        String sql = "SELECT p.*, c.category_name, s.supplier_name FROM products p " +
                     "LEFT JOIN product_categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN suppliers s ON p.supplier_id = s.supplier_id " +
                     "WHERE p.product_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving product by ID", e);
        }
        return null;
    }

    /**
     * Get product by SKU
     */
    public Product getProductBySku(String sku) {
        String sql = "SELECT p.*, c.category_name, s.supplier_name FROM products p " +
                     "LEFT JOIN product_categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN suppliers s ON p.supplier_id = s.supplier_id " +
                     "WHERE p.sku = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sku);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving product by SKU", e);
        }
        return null;
    }

    /**
     * Get all active products
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name, s.supplier_name FROM products p " +
                     "LEFT JOIN product_categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN suppliers s ON p.supplier_id = s.supplier_id " +
                     "WHERE p.status = 'ACTIVE' ORDER BY p.product_name";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all products", e);
        }
        return products;
    }

    /**
     * Get products by category
     */
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name, s.supplier_name FROM products p " +
                     "LEFT JOIN product_categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN suppliers s ON p.supplier_id = s.supplier_id " +
                     "WHERE p.category_id = ? AND p.status = 'ACTIVE' ORDER BY p.product_name";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving products by category", e);
        }
        return products;
    }

    /**
     * Update product
     */
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET product_name = ?, description = ?, " +
                     "unit_price = ?, reorder_level = ?, reorder_quantity = ?, " +
                     "supplier_id = ?, status = ? WHERE product_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, product.getUnitPrice());
            stmt.setInt(4, product.getReorderLevel());
            stmt.setInt(5, product.getReorderQuantity());
            stmt.setInt(6, product.getSupplierId());
            stmt.setString(7, product.getStatus());
            stmt.setInt(8, product.getProductId());

            int rowsUpdated = stmt.executeUpdate();
            logger.info("Product updated successfully: {}", product.getProductId());
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("Error updating product", e);
            return false;
        }
    }

    /**
     * Delete product
     */
    public boolean deleteProduct(int productId) {
        String sql = "UPDATE products SET status = 'DISCONTINUED' WHERE product_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            int rowsUpdated = stmt.executeUpdate();
            logger.info("Product deleted (marked as discontinued): {}", productId);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("Error deleting product", e);
            return false;
        }
    }

    /**
     * Get total product count
     */
    public int getTotalProductCount() {
        String sql = "SELECT COUNT(*) as total FROM products WHERE status = 'ACTIVE'";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            logger.error("Error getting product count", e);
        }
        return 0;
    }

    /**
     * Search products by name
     */
    public List<Product> searchProducts(String searchTerm) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name, s.supplier_name FROM products p " +
                     "LEFT JOIN product_categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN suppliers s ON p.supplier_id = s.supplier_id " +
                     "WHERE (p.product_name LIKE ? OR p.sku LIKE ?) AND p.status = 'ACTIVE'";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            logger.error("Error searching products", e);
        }
        return products;
    }

    /**
     * Map ResultSet to Product object
     */
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setSku(rs.getString("sku"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setCategoryName(rs.getString("category_name"));
        product.setDescription(rs.getString("description"));
        product.setUnitPrice(rs.getBigDecimal("unit_price"));
        product.setReorderLevel(rs.getInt("reorder_level"));
        product.setReorderQuantity(rs.getInt("reorder_quantity"));
        product.setSupplierId(rs.getInt("supplier_id"));
        product.setSupplierName(rs.getString("supplier_name"));
        product.setStatus(rs.getString("status"));
        product.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        product.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        return product;
    }
}
