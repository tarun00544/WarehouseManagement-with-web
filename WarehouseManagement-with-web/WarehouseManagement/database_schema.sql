-- ============================================
-- Warehouse Management System Database Schema
-- ============================================

-- Create Database
CREATE DATABASE IF NOT EXISTS warehouse_db;
USE warehouse_db;

-- ============================================
-- SUPPLIERS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS suppliers (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_name VARCHAR(100) NOT NULL UNIQUE,
    contact_person VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    city VARCHAR(50),
    country VARCHAR(50),
    payment_terms VARCHAR(50),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- PRODUCT CATEGORIES TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS product_categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- PRODUCTS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(150) NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    category_id INT NOT NULL,
    description TEXT,
    unit_price DECIMAL(10, 2) NOT NULL,
    reorder_level INT NOT NULL,
    reorder_quantity INT NOT NULL,
    supplier_id INT NOT NULL,
    status ENUM('ACTIVE', 'DISCONTINUED') DEFAULT 'ACTIVE',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES product_categories(category_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    INDEX idx_sku (sku),
    INDEX idx_category (category_id),
    INDEX idx_supplier (supplier_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- WAREHOUSE LOCATIONS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS warehouse_locations (
    location_id INT PRIMARY KEY AUTO_INCREMENT,
    location_code VARCHAR(50) NOT NULL UNIQUE,
    location_name VARCHAR(100) NOT NULL,
    warehouse_section VARCHAR(50),
    capacity INT,
    current_utilization INT DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- STOCK INVENTORY TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS stock_inventory (
    stock_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL UNIQUE,
    current_quantity INT NOT NULL DEFAULT 0,
    reserved_quantity INT DEFAULT 0,
    available_quantity INT GENERATED ALWAYS AS (current_quantity - reserved_quantity) STORED,
    location_id INT,
    last_stock_check TIMESTAMP,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (location_id) REFERENCES warehouse_locations(location_id),
    INDEX idx_product (product_id),
    INDEX idx_available (available_quantity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- PURCHASE ORDERS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS purchase_orders (
    po_id INT PRIMARY KEY AUTO_INCREMENT,
    po_number VARCHAR(50) NOT NULL UNIQUE,
    supplier_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expected_delivery_date DATE,
    actual_delivery_date DATE,
    total_amount DECIMAL(12, 2),
    status ENUM('DRAFT', 'SUBMITTED', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'DRAFT',
    notes TEXT,
    created_by VARCHAR(100),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    INDEX idx_supplier (supplier_id),
    INDEX idx_status (status),
    INDEX idx_date (order_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- PURCHASE ORDER ITEMS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS purchase_order_items (
    po_item_id INT PRIMARY KEY AUTO_INCREMENT,
    po_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity_ordered INT NOT NULL,
    quantity_received INT DEFAULT 0,
    unit_price DECIMAL(10, 2) NOT NULL,
    line_total DECIMAL(12, 2) GENERATED ALWAYS AS (quantity_ordered * unit_price) STORED,
    FOREIGN KEY (po_id) REFERENCES purchase_orders(po_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    INDEX idx_po (po_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- SALES ORDERS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS sales_orders (
    sales_order_id INT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100),
    customer_phone VARCHAR(20),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivery_date DATE,
    total_amount DECIMAL(12, 2),
    status ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    notes TEXT,
    created_by VARCHAR(100),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_status (status),
    INDEX idx_date (order_date),
    INDEX idx_customer (customer_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- SALES ORDER ITEMS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS sales_order_items (
    sales_item_id INT PRIMARY KEY AUTO_INCREMENT,
    sales_order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity_ordered INT NOT NULL,
    quantity_shipped INT DEFAULT 0,
    unit_price DECIMAL(10, 2) NOT NULL,
    line_total DECIMAL(12, 2) GENERATED ALWAYS AS (quantity_ordered * unit_price) STORED,
    FOREIGN KEY (sales_order_id) REFERENCES sales_orders(sales_order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    INDEX idx_sales_order (sales_order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- INVENTORY ALERTS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS inventory_alerts (
    alert_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    alert_type ENUM('LOW_STOCK', 'OVERSTOCK', 'EXPIRED', 'DAMAGED', 'CRITICAL') NOT NULL,
    alert_message VARCHAR(255) NOT NULL,
    current_stock INT,
    threshold_value INT,
    severity ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM',
    status ENUM('ACTIVE', 'RESOLVED', 'IGNORED') DEFAULT 'ACTIVE',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_date TIMESTAMP NULL,
    resolved_by VARCHAR(100),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    INDEX idx_product (product_id),
    INDEX idx_status (status),
    INDEX idx_date (created_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- STOCK MOVEMENT HISTORY TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS stock_movement_history (
    movement_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    movement_type ENUM('INBOUND', 'OUTBOUND', 'ADJUSTMENT', 'RETURN') NOT NULL,
    quantity INT NOT NULL,
    reference_number VARCHAR(50),
    from_location_id INT,
    to_location_id INT,
    notes TEXT,
    created_by VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (from_location_id) REFERENCES warehouse_locations(location_id),
    FOREIGN KEY (to_location_id) REFERENCES warehouse_locations(location_id),
    INDEX idx_product (product_id),
    INDEX idx_date (created_date),
    INDEX idx_type (movement_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- WAREHOUSE REPORTS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS warehouse_reports (
    report_id INT PRIMARY KEY AUTO_INCREMENT,
    report_type VARCHAR(100) NOT NULL,
    report_name VARCHAR(150) NOT NULL,
    report_data LONGTEXT,
    generated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    generated_by VARCHAR(100),
    status ENUM('GENERATED', 'ARCHIVED') DEFAULT 'GENERATED',
    INDEX idx_type (report_type),
    INDEX idx_date (generated_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- CREATE INDEXES FOR OPTIMIZATION
-- ============================================
CREATE INDEX idx_stock_product ON stock_inventory(product_id);
CREATE INDEX idx_stock_location ON stock_inventory(location_id);
CREATE INDEX idx_po_date ON purchase_orders(order_date);
CREATE INDEX idx_so_date ON sales_orders(order_date);
CREATE INDEX idx_movement_product_date ON stock_movement_history(product_id, created_date);

-- ============================================
-- INSERT SAMPLE DATA
-- ============================================

-- Insert Suppliers
INSERT INTO suppliers (supplier_name, contact_person, email, phone, address, city, country, payment_terms, status) VALUES
('TechSupply Inc', 'John Smith', 'john@techsupply.com', '+1-555-0101', '123 Tech Street', 'San Francisco', 'USA', 'Net 30', 'ACTIVE'),
('Global Logistics', 'Maria Garcia', 'maria@globallog.com', '+1-555-0102', '456 Commerce Ave', 'New York', 'USA', 'Net 45', 'ACTIVE'),
('Electronics Wholesale', 'David Chen', 'david@elecwhc.com', '+1-555-0103', '789 Industrial Rd', 'Chicago', 'USA', 'Net 30', 'ACTIVE');

-- Insert Product Categories
INSERT INTO product_categories (category_name, description) VALUES
('Electronics', 'Electronic components and devices'),
('Hardware', 'Industrial hardware and tools'),
('Packaging', 'Packaging materials and supplies'),
('Office Supplies', 'Office equipment and supplies');

-- Insert Products
INSERT INTO products (product_name, sku, category_id, description, unit_price, reorder_level, reorder_quantity, supplier_id, status) VALUES
('Laptop Computer', 'LAP-001', 1, 'High-performance laptop', 999.99, 10, 25, 1, 'ACTIVE'),
('Monitor 27 inch', 'MON-001', 1, 'Full HD LED Monitor', 299.99, 15, 30, 1, 'ACTIVE'),
('Keyboard Mechanical', 'KEY-001', 1, 'RGB Mechanical Keyboard', 149.99, 20, 40, 1, 'ACTIVE'),
('Mouse Wireless', 'MOU-001', 1, 'Ergonomic wireless mouse', 49.99, 30, 60, 1, 'ACTIVE'),
('USB Cable Type-C', 'USB-001', 1, 'USB 3.1 Type-C Cable', 19.99, 50, 100, 1, 'ACTIVE'),
('Industrial Drill', 'DRL-001', 2, 'Heavy-duty power drill', 249.99, 8, 15, 2, 'ACTIVE'),
('Tool Set 50 piece', 'TST-001', 2, 'Comprehensive tool set', 149.99, 5, 10, 2, 'ACTIVE'),
('Packaging Tape', 'PKG-001', 3, 'Heavy-duty packing tape', 9.99, 100, 200, 2, 'ACTIVE'),
('Cardboard Boxes', 'BOX-001', 3, 'Standard shipping boxes', 2.99, 200, 500, 2, 'ACTIVE'),
('Office Desk', 'DSK-001', 4, 'Modern office desk', 399.99, 10, 20, 3, 'ACTIVE');

-- Insert Warehouse Locations
INSERT INTO warehouse_locations (location_code, location_name, warehouse_section, capacity, status) VALUES
('ZONE-A1', 'Zone A - Section 1', 'Electronics', 500, 'ACTIVE'),
('ZONE-A2', 'Zone A - Section 2', 'Electronics', 500, 'ACTIVE'),
('ZONE-B1', 'Zone B - Section 1', 'Hardware', 400, 'ACTIVE'),
('ZONE-B2', 'Zone B - Section 2', 'Hardware', 400, 'ACTIVE'),
('ZONE-C1', 'Zone C - Section 1', 'Packaging', 600, 'ACTIVE'),
('ZONE-D1', 'Zone D - Section 1', 'Office', 300, 'ACTIVE');

-- Insert Stock Inventory
INSERT INTO stock_inventory (product_id, current_quantity, reserved_quantity, location_id) VALUES
(1, 45, 5, 1),
(2, 60, 10, 1),
(3, 85, 15, 1),
(4, 120, 20, 1),
(5, 250, 50, 1),
(6, 35, 3, 3),
(7, 28, 5, 3),
(8, 450, 50, 5),
(9, 800, 100, 5),
(10, 22, 2, 6);

COMMIT;
