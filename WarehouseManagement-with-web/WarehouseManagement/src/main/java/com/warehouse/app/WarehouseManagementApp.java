package com.warehouse.app;

import com.warehouse.dao.*;
import com.warehouse.model.*;
import com.warehouse.service.InventoryService;
import com.warehouse.service.OrderService;
import com.warehouse.service.ReportService;
import com.warehouse.util.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * WarehouseManagementApp - Main application entry point
 */
public class WarehouseManagementApp {
    private static final Logger logger = LoggerFactory.getLogger(WarehouseManagementApp.class);
    private static final Scanner scanner = new Scanner(System.in);

    private InventoryService inventoryService;
    private OrderService orderService;
    private ReportService reportService;
    private ProductDAO productDAO;
    private SupplierDAO supplierDAO;
    private StockDAO stockDAO;
    private InventoryAlertDAO alertDAO;

    public WarehouseManagementApp() {
        this.inventoryService = new InventoryService();
        this.orderService = new OrderService();
        this.reportService = new ReportService();
        this.productDAO = new ProductDAO();
        this.supplierDAO = new SupplierDAO();
        this.stockDAO = new StockDAO();
        this.alertDAO = new InventoryAlertDAO();
    }

    public static void main(String[] args) {
        logger.info("Starting Warehouse Management System");

        // Test database connection
        if (!DatabaseConfig.testConnection()) {
            System.err.println("Failed to connect to database. Please check your connection settings.");
            System.err.println("Update application.properties with correct database credentials.");
            logger.error("Database connection failed");
            return;
        }

        WarehouseManagementApp app = new WarehouseManagementApp();
        app.run();
    }

    /**
     * Main application loop
     */
    public void run() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput();
            running = handleMainMenuChoice(choice);
        }
        scanner.close();
        System.out.println("Thank you for using Warehouse Management System. Goodbye!");
        logger.info("Application terminated");
    }

    // ============== MENU DISPLAY METHODS ==============

    private void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║       WAREHOUSE MANAGEMENT SYSTEM - MAIN MENU               ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("1. Product Management");
        System.out.println("2. Stock/Inventory Management");
        System.out.println("3. Supplier Management");
        System.out.println("4. Purchase Orders");
        System.out.println("5. Sales Orders");
        System.out.println("6. Inventory Alerts");
        System.out.println("7. Dashboard & Analytics");
        System.out.println("8. Reports");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void displayProductMenu() {
        System.out.println("\n--- PRODUCT MANAGEMENT ---");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Search Product");
        System.out.println("4. Update Product");
        System.out.println("5. View Product Details");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private void displayInventoryMenu() {
        System.out.println("\n--- STOCK/INVENTORY MANAGEMENT ---");
        System.out.println("1. View All Stock");
        System.out.println("2. Update Stock Quantity");
        System.out.println("3. View Low Stock Items");
        System.out.println("4. View Overstock Items");
        System.out.println("5. View Stock by Product");
        System.out.println("6. Check Inventory Value");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private void displaySupplierMenu() {
        System.out.println("\n--- SUPPLIER MANAGEMENT ---");
        System.out.println("1. Add Supplier");
        System.out.println("2. View All Suppliers");
        System.out.println("3. View Supplier Details");
        System.out.println("4. Update Supplier");
        System.out.println("5. Deactivate Supplier");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    // ============== MENU HANDLER METHODS ==============

    private boolean handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                handleProductManagement();
                break;
            case 2:
                handleInventoryManagement();
                break;
            case 3:
                handleSupplierManagement();
                break;
            case 4:
                handlePurchaseOrders();
                break;
            case 5:
                handleSalesOrders();
                break;
            case 6:
                handleInventoryAlerts();
                break;
            case 7:
                displayDashboard();
                break;
            case 8:
                displayReports();
                break;
            case 0:
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    private void handleProductManagement() {
        boolean inMenu = true;
        while (inMenu) {
            displayProductMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewAllProducts();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    viewProductDetails();
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void handleInventoryManagement() {
        boolean inMenu = true;
        while (inMenu) {
            displayInventoryMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    viewAllStock();
                    break;
                case 2:
                    updateStockQuantity();
                    break;
                case 3:
                    viewLowStockItems();
                    break;
                case 4:
                    viewOverstockItems();
                    break;
                case 5:
                    viewStockByProduct();
                    break;
                case 6:
                    checkInventoryValue();
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void handleSupplierManagement() {
        boolean inMenu = true;
        while (inMenu) {
            displaySupplierMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addSupplier();
                    break;
                case 2:
                    viewAllSuppliers();
                    break;
                case 3:
                    viewSupplierDetails();
                    break;
                case 4:
                    updateSupplier();
                    break;
                case 5:
                    deactivateSupplier();
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void handlePurchaseOrders() {
        System.out.println("\n--- PURCHASE ORDERS ---");
        System.out.println("1. Create Purchase Order");
        System.out.println("2. View All Purchase Orders");
        System.out.println("3. View Pending Orders");
        System.out.println("4. Update Order Status");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                createPurchaseOrder();
                break;
            case 2:
                viewAllPurchaseOrders();
                break;
            case 3:
                viewPendingPurchaseOrders();
                break;
            case 4:
                updatePurchaseOrderStatus();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void handleSalesOrders() {
        System.out.println("\n--- SALES ORDERS ---");
        System.out.println("1. Create Sales Order");
        System.out.println("2. View All Sales Orders");
        System.out.println("3. View Pending Orders");
        System.out.println("4. Update Order Status");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                createSalesOrder();
                break;
            case 2:
                viewAllSalesOrders();
                break;
            case 3:
                viewPendingSalesOrders();
                break;
            case 4:
                updateSalesOrderStatus();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void handleInventoryAlerts() {
        System.out.println("\n--- INVENTORY ALERTS ---");
        System.out.println("1. View Active Alerts");
        System.out.println("2. View Alerts by Type");
        System.out.println("3. View Critical Alerts");
        System.out.println("4. Resolve Alert");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                viewActiveAlerts();
                break;
            case 2:
                viewAlertsByType();
                break;
            case 3:
                viewCriticalAlerts();
                break;
            case 4:
                resolveAlert();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // ============== PRODUCT OPERATIONS ==============

    private void addProduct() {
        System.out.println("\n--- ADD NEW PRODUCT ---");
        System.out.print("Product Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("SKU: ");
        String sku = scanner.nextLine().trim();

        System.out.print("Category ID: ");
        int categoryId = getIntInput();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Unit Price: ");
        BigDecimal price = getBigDecimalInput();

        System.out.print("Reorder Level: ");
        int reorderLevel = getIntInput();

        System.out.print("Reorder Quantity: ");
        int reorderQty = getIntInput();

        System.out.print("Supplier ID: ");
        int supplierId = getIntInput();

        Product product = new Product();
        product.setProductName(name);
        product.setSku(sku);
        product.setCategoryId(categoryId);
        product.setDescription(description);
        product.setUnitPrice(price);
        product.setReorderLevel(reorderLevel);
        product.setReorderQuantity(reorderQty);
        product.setSupplierId(supplierId);
        product.setStatus("ACTIVE");

        if (productDAO.addProduct(product)) {
            System.out.println("✓ Product added successfully!");
            logger.info("Product added: {}", sku);
        } else {
            System.out.println("✗ Failed to add product.");
        }
    }

    private void viewAllProducts() {
        List<Product> products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ALL PRODUCTS                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.printf("%-5s %-25s %-10s %-15s %-10s\n", "ID", "Name", "SKU", "Category", "Price");
        System.out.println("─".repeat(65));

        for (Product p : products) {
            System.out.printf("%-5d %-25s %-10s %-15s %-10.2f\n",
                    p.getProductId(),
                    truncate(p.getProductName(), 23),
                    p.getSku(),
                    p.getCategoryName() != null ? truncate(p.getCategoryName(), 13) : "N/A",
                    p.getUnitPrice());
        }
    }

    private void searchProduct() {
        System.out.print("Search term: ");
        String term = scanner.nextLine().trim();

        List<Product> results = productDAO.searchProducts(term);
        if (results.isEmpty()) {
            System.out.println("No products found matching: " + term);
            return;
        }

        System.out.println("\n--- SEARCH RESULTS ---");
        System.out.printf("%-5s %-25s %-10s %-15s\n", "ID", "Name", "SKU", "Category");
        for (Product p : results) {
            System.out.printf("%-5d %-25s %-10s %-15s\n",
                    p.getProductId(),
                    truncate(p.getProductName(), 23),
                    p.getSku(),
                    p.getCategoryName());
        }
    }

    private void updateProduct() {
        System.out.print("Product ID to update: ");
        int productId = getIntInput();

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("\nCurrent Product: " + product);
        System.out.print("New Unit Price (current: " + product.getUnitPrice() + "): ");
        BigDecimal newPrice = getBigDecimalInput();
        product.setUnitPrice(newPrice);

        if (productDAO.updateProduct(product)) {
            System.out.println("✓ Product updated successfully!");
        } else {
            System.out.println("✗ Failed to update product.");
        }
    }

    private void viewProductDetails() {
        System.out.print("Product ID: ");
        int productId = getIntInput();

        Product product = productDAO.getProductById(productId);
        if (product != null) {
            System.out.println("\n--- PRODUCT DETAILS ---");
            System.out.println("ID: " + product.getProductId());
            System.out.println("Name: " + product.getProductName());
            System.out.println("SKU: " + product.getSku());
            System.out.println("Category: " + product.getCategoryName());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Unit Price: " + product.getUnitPrice());
            System.out.println("Reorder Level: " + product.getReorderLevel());
            System.out.println("Reorder Quantity: " + product.getReorderQuantity());
            System.out.println("Supplier: " + product.getSupplierName());
            System.out.println("Status: " + product.getStatus());
        } else {
            System.out.println("Product not found.");
        }
    }

    // ============== STOCK OPERATIONS ==============

    private void viewAllStock() {
        List<Stock> stocks = stockDAO.getAllStock();
        if (stocks.isEmpty()) {
            System.out.println("No stock records found.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    STOCK INVENTORY                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.printf("%-10s %-25s %-10s %-10s %-10s\n", "SKU", "Product", "Current", "Reserved", "Available");
        System.out.println("─".repeat(65));

        for (Stock s : stocks) {
            System.out.printf("%-10s %-25s %-10d %-10d %-10d\n",
                    s.getSku(),
                    truncate(s.getProductName(), 23),
                    s.getCurrentQuantity(),
                    s.getReservedQuantity(),
                    s.getAvailableQuantity());
        }
    }

    private void updateStockQuantity() {
        System.out.print("Product ID: ");
        int productId = getIntInput();

        System.out.print("Quantity: ");
        int quantity = getIntInput();

        System.out.println("Movement Type: 1=INBOUND, 2=OUTBOUND");
        System.out.print("Select: ");
        int type = getIntInput();
        String movementType = type == 1 ? "INBOUND" : "OUTBOUND";

        inventoryService.updateStock(productId, quantity, movementType);
        System.out.println("✓ Stock updated successfully!");
    }

    private void viewLowStockItems() {
        List<Stock> lowStock = stockDAO.getLowStockItems(Integer.MAX_VALUE);
        if (lowStock.isEmpty()) {
            System.out.println("No low stock items.");
            return;
        }

        System.out.println("\n--- LOW STOCK ITEMS ---");
        System.out.printf("%-10s %-25s %-12s %-12s\n", "SKU", "Product", "Available", "Reorder Lvl");
        for (Stock s : lowStock) {
            System.out.printf("%-10s %-25s %-12d %-12d\n",
                    s.getSku(),
                    truncate(s.getProductName(), 23),
                    s.getAvailableQuantity(),
                    0); // Would need reorder level from product
        }
    }

    private void viewOverstockItems() {
        List<Stock> overstock = stockDAO.getOverstockItems();
        if (overstock.isEmpty()) {
            System.out.println("No overstock items.");
            return;
        }

        System.out.println("\n--- OVERSTOCK ITEMS ---");
        System.out.printf("%-10s %-25s %-10s\n", "SKU", "Product", "Current Qty");
        for (Stock s : overstock) {
            System.out.printf("%-10s %-25s %-10d\n",
                    s.getSku(),
                    truncate(s.getProductName(), 23),
                    s.getCurrentQuantity());
        }
    }

    private void viewStockByProduct() {
        System.out.print("Product ID: ");
        int productId = getIntInput();

        Stock stock = stockDAO.getStockByProductId(productId);
        if (stock != null) {
            System.out.println("\n--- STOCK DETAILS ---");
            System.out.println("Product: " + stock.getProductName());
            System.out.println("SKU: " + stock.getSku());
            System.out.println("Current Quantity: " + stock.getCurrentQuantity());
            System.out.println("Reserved Quantity: " + stock.getReservedQuantity());
            System.out.println("Available Quantity: " + stock.getAvailableQuantity());
            System.out.println("Location: " + stock.getLocationName());
        } else {
            System.out.println("Stock not found.");
        }
    }

    private void checkInventoryValue() {
        int totalValue = stockDAO.getTotalInventoryValue();
        int totalQty = stockDAO.getTotalInventoryQuantity();

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        INVENTORY VALUE SUMMARY              ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ Total Inventory Quantity: " + String.format("%15d", totalQty) + " ║");
        System.out.println("║ Total Inventory Value:    ₹" + String.format("%15d", totalValue) + " ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }

    // ============== SUPPLIER OPERATIONS ==============

    private void addSupplier() {
        System.out.println("\n--- ADD NEW SUPPLIER ---");
        System.out.print("Supplier Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Contact Person: ");
        String contact = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();

        Supplier supplier = new Supplier(name, contact, email, phone);
        supplier.setStatus("ACTIVE");

        if (supplierDAO.addSupplier(supplier)) {
            System.out.println("✓ Supplier added successfully!");
        } else {
            System.out.println("✗ Failed to add supplier.");
        }
    }

    private void viewAllSuppliers() {
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ALL SUPPLIERS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.printf("%-5s %-25s %-20s %-15s\n", "ID", "Name", "Contact", "Email");
        System.out.println("─".repeat(65));

        for (Supplier s : suppliers) {
            System.out.printf("%-5d %-25s %-20s %-15s\n",
                    s.getSupplierId(),
                    truncate(s.getSupplierName(), 23),
                    truncate(s.getContactPerson(), 18),
                    s.getEmail() != null ? s.getEmail() : "N/A");
        }
    }

    private void viewSupplierDetails() {
        System.out.print("Supplier ID: ");
        int supplierId = getIntInput();

        Supplier supplier = supplierDAO.getSupplierById(supplierId);
        if (supplier != null) {
            System.out.println("\n--- SUPPLIER DETAILS ---");
            System.out.println("ID: " + supplier.getSupplierId());
            System.out.println("Name: " + supplier.getSupplierName());
            System.out.println("Contact Person: " + supplier.getContactPerson());
            System.out.println("Email: " + supplier.getEmail());
            System.out.println("Phone: " + supplier.getPhone());
            System.out.println("Address: " + supplier.getAddress());
            System.out.println("City: " + supplier.getCity());
            System.out.println("Country: " + supplier.getCountry());
            System.out.println("Payment Terms: " + supplier.getPaymentTerms());
            System.out.println("Status: " + supplier.getStatus());
        } else {
            System.out.println("Supplier not found.");
        }
    }

    private void updateSupplier() {
        System.out.print("Supplier ID: ");
        int supplierId = getIntInput();

        Supplier supplier = supplierDAO.getSupplierById(supplierId);
        if (supplier == null) {
            System.out.println("Supplier not found.");
            return;
        }

        System.out.print("Email (current: " + supplier.getEmail() + "): ");
        String newEmail = scanner.nextLine().trim();
        if (!newEmail.isEmpty()) supplier.setEmail(newEmail);

        System.out.print("Phone (current: " + supplier.getPhone() + "): ");
        String newPhone = scanner.nextLine().trim();
        if (!newPhone.isEmpty()) supplier.setPhone(newPhone);

        if (supplierDAO.updateSupplier(supplier)) {
            System.out.println("✓ Supplier updated successfully!");
        } else {
            System.out.println("✗ Failed to update supplier.");
        }
    }

    private void deactivateSupplier() {
        System.out.print("Supplier ID: ");
        int supplierId = getIntInput();

        if (supplierDAO.deactivateSupplier(supplierId)) {
            System.out.println("✓ Supplier deactivated successfully!");
        } else {
            System.out.println("✗ Failed to deactivate supplier.");
        }
    }

    // ============== PURCHASE ORDER OPERATIONS ==============

    private void createPurchaseOrder() {
        System.out.println("\n--- CREATE PURCHASE ORDER ---");
        System.out.print("PO Number: ");
        String poNumber = scanner.nextLine().trim();

        System.out.print("Supplier ID: ");
        int supplierId = getIntInput();

        System.out.print("Expected Delivery Date (YYYY-MM-DD): ");
        LocalDate deliveryDate = getDateInput();

        System.out.print("Notes: ");
        String notes = scanner.nextLine().trim();

        if (orderService.createPurchaseOrder(poNumber, supplierId, deliveryDate, notes)) {
            System.out.println("✓ Purchase order created successfully!");
        } else {
            System.out.println("✗ Failed to create purchase order.");
        }
    }

    private void viewAllPurchaseOrders() {
        List<PurchaseOrder> orders = orderService.getAllPurchaseOrders();
        if (orders.isEmpty()) {
            System.out.println("No purchase orders found.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║               ALL PURCHASE ORDERS                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.printf("%-8s %-15s %-25s %-15s\n", "ID", "PO Number", "Supplier", "Status");
        System.out.println("─".repeat(63));

        for (PurchaseOrder po : orders) {
            System.out.printf("%-8d %-15s %-25s %-15s\n",
                    po.getPoId(),
                    po.getPoNumber(),
                    truncate(po.getSupplierName(), 23),
                    po.getStatus());
        }
    }

    private void viewPendingPurchaseOrders() {
        int pendingCount = orderService.getPendingPurchaseOrderCount();
        System.out.println("Pending Purchase Orders: " + pendingCount);

        List<PurchaseOrder> orders = orderService.getPurchaseOrdersByStatus("DRAFT");
        if (!orders.isEmpty()) {
            System.out.println("\nDraft Orders:");
            for (PurchaseOrder po : orders) {
                System.out.println("  " + po.getPoNumber() + " - " + po.getSupplierName());
            }
        }
    }

    private void updatePurchaseOrderStatus() {
        System.out.print("PO ID: ");
        int poId = getIntInput();

        System.out.println("New Status: 1=SUBMITTED, 2=CONFIRMED, 3=SHIPPED, 4=DELIVERED");
        System.out.print("Select: ");
        int statusChoice = getIntInput();
        String[] statuses = {"SUBMITTED", "CONFIRMED", "SHIPPED", "DELIVERED"};
        String newStatus = statusChoice > 0 && statusChoice <= statuses.length ? statuses[statusChoice - 1] : "SUBMITTED";

        if (orderService.updatePurchaseOrderStatus(poId, newStatus)) {
            System.out.println("✓ PO status updated to: " + newStatus);
        } else {
            System.out.println("✗ Failed to update PO status.");
        }
    }

    // ============== SALES ORDER OPERATIONS ==============

    private void createSalesOrder() {
        System.out.println("\n--- CREATE SALES ORDER ---");
        System.out.print("Order Number: ");
        String orderNumber = scanner.nextLine().trim();

        System.out.print("Customer Name: ");
        String customerName = scanner.nextLine().trim();

        System.out.print("Customer Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Customer Phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Delivery Date (YYYY-MM-DD): ");
        LocalDate deliveryDate = getDateInput();

        System.out.print("Notes: ");
        String notes = scanner.nextLine().trim();

        if (orderService.createSalesOrder(orderNumber, customerName, email, phone, deliveryDate, notes)) {
            System.out.println("✓ Sales order created successfully!");
        } else {
            System.out.println("✗ Failed to create sales order.");
        }
    }

    private void viewAllSalesOrders() {
        List<SalesOrder> orders = orderService.getAllSalesOrders();
        if (orders.isEmpty()) {
            System.out.println("No sales orders found.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                ALL SALES ORDERS                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.printf("%-8s %-15s %-25s %-15s\n", "ID", "Order #", "Customer", "Status");
        System.out.println("─".repeat(63));

        for (SalesOrder so : orders) {
            System.out.printf("%-8d %-15s %-25s %-15s\n",
                    so.getSalesOrderId(),
                    so.getOrderNumber(),
                    truncate(so.getCustomerName(), 23),
                    so.getStatus());
        }
    }

    private void viewPendingSalesOrders() {
        int pendingCount = orderService.getPendingSalesOrderCount();
        System.out.println("Pending Sales Orders: " + pendingCount);

        List<SalesOrder> orders = orderService.getSalesOrdersByStatus("PENDING");
        if (!orders.isEmpty()) {
            System.out.println("\nPending Orders:");
            for (SalesOrder so : orders) {
                System.out.println("  " + so.getOrderNumber() + " - " + so.getCustomerName());
            }
        }
    }

    private void updateSalesOrderStatus() {
        System.out.print("Sales Order ID: ");
        int soId = getIntInput();

        System.out.println("New Status: 1=PROCESSING, 2=SHIPPED, 3=DELIVERED");
        System.out.print("Select: ");
        int statusChoice = getIntInput();
        String[] statuses = {"PROCESSING", "SHIPPED", "DELIVERED"};
        String newStatus = statusChoice > 0 && statusChoice <= statuses.length ? statuses[statusChoice - 1] : "PROCESSING";

        if (orderService.updateSalesOrderStatus(soId, newStatus)) {
            System.out.println("✓ Order status updated to: " + newStatus);
        } else {
            System.out.println("✗ Failed to update order status.");
        }
    }

    // ============== ALERT OPERATIONS ==============

    private void viewActiveAlerts() {
        List<InventoryAlert> alerts = inventoryService.getActiveAlerts();
        if (alerts.isEmpty()) {
            System.out.println("No active alerts.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  ACTIVE ALERTS                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.printf("%-5s %-25s %-15s %-12s\n", "ID", "Product", "Type", "Severity");
        System.out.println("─".repeat(57));

        for (InventoryAlert a : alerts) {
            System.out.printf("%-5d %-25s %-15s %-12s\n",
                    a.getAlertId(),
                    truncate(a.getProductName(), 23),
                    a.getAlertType(),
                    a.getSeverity());
        }
    }

    private void viewAlertsByType() {
        System.out.println("Alert Types: 1=LOW_STOCK, 2=OVERSTOCK, 3=EXPIRED, 4=DAMAGED");
        System.out.print("Select: ");
        int typeChoice = getIntInput();
        String[] types = {"LOW_STOCK", "OVERSTOCK", "EXPIRED", "DAMAGED"};
        String alertType = typeChoice > 0 && typeChoice <= types.length ? types[typeChoice - 1] : "LOW_STOCK";

        List<InventoryAlert> alerts = alertDAO.getAlertsByType(alertType);
        System.out.println("\nAlerts of type: " + alertType);
        for (InventoryAlert a : alerts) {
            System.out.println("  Product: " + a.getProductName() + " | Stock: " + a.getCurrentStock());
        }
    }

    private void viewCriticalAlerts() {
        List<InventoryAlert> alerts = alertDAO.getAlertsBySeverity("CRITICAL");
        if (alerts.isEmpty()) {
            System.out.println("No critical alerts.");
            return;
        }

        System.out.println("\n⚠ CRITICAL ALERTS ⚠");
        System.out.printf("%-5s %-25s %-20s %-10s\n", "ID", "Product", "Message", "Stock");
        for (InventoryAlert a : alerts) {
            System.out.printf("%-5d %-25s %-20s %-10d\n",
                    a.getAlertId(),
                    truncate(a.getProductName(), 23),
                    truncate(a.getAlertMessage(), 18),
                    a.getCurrentStock());
        }
    }

    private void resolveAlert() {
        System.out.print("Alert ID to resolve: ");
        int alertId = getIntInput();

        if (inventoryService.resolveAlert(alertId, "ADMIN")) {
            System.out.println("✓ Alert resolved successfully!");
        } else {
            System.out.println("✗ Failed to resolve alert.");
        }
    }

    // ============== DASHBOARD & ANALYTICS ==============

    private void displayDashboard() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║            WAREHOUSE DASHBOARD                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        // Inventory Summary
        InventoryService.InventorySummary invSummary = inventoryService.getInventorySummary();
        System.out.println("\n┌─ INVENTORY SUMMARY ─────────────────────────────────────┐");
        System.out.println("│ Total Products:        " + String.format("%30d", invSummary.getTotalProducts()) + " │");
        System.out.println("│ Total Quantity:        " + String.format("%30d", invSummary.getTotalQuantity()) + " │");
        System.out.println("│ Total Inventory Value: ₹" + String.format("%29d", invSummary.getTotalInventoryValue()) + " │");
        System.out.println("│ Active Alerts:         " + String.format("%30d", invSummary.getActiveAlerts()) + " │");
        System.out.println("│ Critical Alerts:       " + String.format("%30d", invSummary.getCriticalAlerts()) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        // Order Summary
        OrderService.OrderSummary orderSummary = orderService.getOrderSummary();
        System.out.println("\n┌─ ORDER SUMMARY ─────────────────────────────────────────┐");
        System.out.println("│ Pending Purchase Orders: " + String.format("%22d", orderSummary.getPendingPurchaseOrders()) + " │");
        System.out.println("│ Pending Sales Orders:    " + String.format("%22d", orderSummary.getPendingSalesOrders()) + " │");
        System.out.println("│ Total Sales Revenue:  ₹" + String.format("%23.2f", orderSummary.getTotalRevenue()) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    // ============== REPORTS ==============

    private void displayReports() {
        System.out.println("\n--- REPORTS ---");
        System.out.println("1. Inventory Status Report");
        System.out.println("2. Product Analysis Report");
        System.out.println("3. Stock Movement Report");
        System.out.println("4. Inventory Alerts Report");
        System.out.println("5. Supplier Performance Report");
        System.out.println("6. Purchase Order Analytics");
        System.out.println("7. Sales Order Analytics");
        System.out.print("Select report: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                generateInventoryStatusReport();
                break;
            case 2:
                generateProductAnalysisReport();
                break;
            case 3:
                generateStockMovementReport();
                break;
            case 4:
                generateAlertsReport();
                break;
            case 5:
                generateSupplierReport();
                break;
            case 6:
                generatePOAnalyticsReport();
                break;
            case 7:
                generateSOAnalyticsReport();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void generateInventoryStatusReport() {
        ReportService.InventoryReport report = reportService.generateInventoryReport();
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║            INVENTORY STATUS REPORT                          ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ Generated: " + report.getGeneratedAt());
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("│ Total Products:       " + String.format("%32d", report.getTotalProducts()) + " │");
        System.out.println("│ Total Quantity:       " + String.format("%32d", report.getTotalQuantity()) + " │");
        System.out.println("│ Total Value (₹):      " + String.format("%32d", report.getTotalValue()) + " │");
        System.out.println("│ Low Stock Items:      " + String.format("%32d", report.getLowStockItems()) + " │");
        System.out.println("│ Overstock Items:      " + String.format("%32d", report.getOverstockItems()) + " │");
        System.out.println("│ Active Alerts:        " + String.format("%32d", report.getActiveAlerts()) + " │");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    private void generateProductAnalysisReport() {
        String report = reportService.generateProductAnalysisReport();
        System.out.println(report);
    }

    private void generateStockMovementReport() {
        String report = reportService.generateStockMovementReport();
        System.out.println(report);
    }

    private void generateAlertsReport() {
        String report = reportService.generateAlertsSummaryReport();
        System.out.println(report);
    }

    private void generateSupplierReport() {
        String report = reportService.generateSupplierPerformanceReport();
        System.out.println(report);
    }

    private void generatePOAnalyticsReport() {
        ReportService.PurchaseOrderAnalytics analytics = reportService.generatePurchaseOrderAnalytics();
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║         PURCHASE ORDER ANALYTICS REPORT                     ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("│ Draft Orders:           " + String.format("%30d", analytics.getDraftOrders()) + " │");
        System.out.println("│ Submitted Orders:       " + String.format("%30d", analytics.getSubmittedOrders()) + " │");
        System.out.println("│ Delivered Orders:       " + String.format("%30d", analytics.getDeliveredOrders()) + " │");
        System.out.println("│ Total Pending Orders:   " + String.format("%30d", analytics.getTotalPendingOrders()) + " │");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    private void generateSOAnalyticsReport() {
        ReportService.SalesOrderAnalytics analytics = reportService.generateSalesOrderAnalytics();
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          SALES ORDER ANALYTICS REPORT                       ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("│ Pending Orders:         " + String.format("%30d", analytics.getPendingOrders()) + " │");
        System.out.println("│ Processing Orders:      " + String.format("%30d", analytics.getProcessingOrders()) + " │");
        System.out.println("│ Shipped Orders:         " + String.format("%30d", analytics.getShippedOrders()) + " │");
        System.out.println("│ Delivered Orders:       " + String.format("%30d", analytics.getDeliveredOrders()) + " │");
        System.out.println("│ Total Revenue (₹):      " + String.format("%29.2f", analytics.getTotalRevenue()) + " │");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    // ============== UTILITY METHODS ==============

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private BigDecimal getBigDecimalInput() {
        try {
            return new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private LocalDate getDateInput() {
        try {
            return LocalDate.parse(scanner.nextLine().trim());
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length) + "..." : str;
    }
}
