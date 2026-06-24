# Warehouse Management System

A comprehensive Java-based automated warehouse management system designed to monitor inventory and supply chain operations.

## Project Overview

This enterprise-grade warehouse management system provides complete control over:
- **Product Inventory Management** - Track all products with categories and suppliers
- **Stock Monitoring** - Real-time stock level tracking and alerts
- **Supplier Management** - Maintain supplier information and performance
- **Purchase Orders** - Create and manage purchase orders from suppliers
- **Sales Orders** - Process customer orders and track deliveries
- **Inventory Alerts** - Automatic alerts for low stock, overstock, and critical situations
- **Warehouse Reports** - Comprehensive analytics and reporting
- **Analytics Dashboard** - Real-time visibility into operations

## Technology Stack

- **Language**: Java 11
- **Database**: MySQL 8.0+
- **Build Tool**: Maven 3.6+
- **Connection Pool**: JDBC with connection management
- **Logging**: SLF4J with Logback
- **JSON Processing**: Gson

## Project Structure

```
WarehouseManagement/
├── pom.xml                                    # Maven configuration
├── database_schema.sql                        # Database schema and sample data
├── src/main/
│   ├── java/com/warehouse/
│   │   ├── app/
│   │   │   └── WarehouseManagementApp.java   # Main application entry point
│   │   ├── dao/
│   │   │   ├── ProductDAO.java               # Product data access
│   │   │   ├── SupplierDAO.java              # Supplier data access
│   │   │   ├── StockDAO.java                 # Stock inventory data access
│   │   │   ├── PurchaseOrderDAO.java         # Purchase order data access
│   │   │   ├── SalesOrderDAO.java            # Sales order data access
│   │   │   └── InventoryAlertDAO.java        # Inventory alert data access
│   │   ├── model/
│   │   │   ├── Product.java                  # Product entity
│   │   │   ├── Supplier.java                 # Supplier entity
│   │   │   ├── Stock.java                    # Stock entity
│   │   │   ├── PurchaseOrder.java            # Purchase order entity
│   │   │   ├── SalesOrder.java               # Sales order entity
│   │   │   └── InventoryAlert.java           # Inventory alert entity
│   │   ├── service/
│   │   │   ├── InventoryService.java         # Inventory business logic
│   │   │   ├── OrderService.java             # Order business logic
│   │   │   └── ReportService.java            # Report and analytics logic
│   │   └── util/
│   │       └── DatabaseConfig.java           # Database configuration
│   └── resources/
│       └── application.properties             # Application configuration
└── README.md                                  # This file
```

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- MySQL Server 8.0 or higher
- Maven 3.6 or higher
- Git (for version control)

## Installation & Setup

### 1. Clone or Download the Project

```bash
cd WarehouseManagement
```

### 2. Create MySQL Database

```bash
mysql -u root -p < database_schema.sql
```

Or manually:
1. Open MySQL workbench or command line
2. Run the SQL commands from `database_schema.sql`

### 3. Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/warehouse_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=root
db.password=YOUR_PASSWORD
db.pool.size=10
```

### 4. Build the Project

```bash
mvn clean package
```

### 5. Run the Application

```bash
java -cp target/warehouse-management-system-1.0.0.jar com.warehouse.app.WarehouseManagementApp
```

Or using Maven:
```bash
mvn exec:java -Dexec.mainClass="com.warehouse.app.WarehouseManagementApp"
```

## Features & Functionality

### 1. Product Management
- ✅ Add new products with details
- ✅ View all products with categories
- ✅ Search products by name or SKU
- ✅ Update product information
- ✅ View product details
- ✅ Track product reorder levels

### 2. Stock Management
- ✅ Real-time stock quantity tracking
- ✅ Reserved vs available quantity management
- ✅ Track stock by warehouse location
- ✅ Update stock with movement types (INBOUND/OUTBOUND)
- ✅ Monitor total inventory value
- ✅ Identify low and overstock situations

### 3. Supplier Management
- ✅ Add supplier information
- ✅ Maintain contact details
- ✅ Track supplier status (ACTIVE/INACTIVE)
- ✅ Update supplier information
- ✅ Supplier performance analytics

### 4. Purchase Orders
- ✅ Create purchase orders from suppliers
- ✅ Track PO status (DRAFT → SUBMITTED → CONFIRMED → SHIPPED → DELIVERED)
- ✅ View all purchase orders
- ✅ Filter by status
- ✅ Monitor pending orders
- ✅ Calculate order totals

### 5. Sales Orders
- ✅ Create customer sales orders
- ✅ Track order status (PENDING → PROCESSING → SHIPPED → DELIVERED)
- ✅ Maintain customer information
- ✅ Monitor pending orders
- ✅ Calculate order revenue
- ✅ Track delivery dates

### 6. Inventory Alerts
- ✅ Automatic low-stock alerts
- ✅ Overstock warnings
- ✅ Critical alert notifications
- ✅ Alert severity levels (LOW, MEDIUM, HIGH, CRITICAL)
- ✅ Resolve alerts
- ✅ Alert type filtering (LOW_STOCK, OVERSTOCK, EXPIRED, DAMAGED)

### 7. Dashboard & Analytics
- ✅ Real-time inventory summary
- ✅ Total inventory value calculation
- ✅ Order pipeline overview
- ✅ Alert status summary
- ✅ Revenue tracking
- ✅ Supplier performance metrics

### 8. Reports
- ✅ Inventory Status Report - Overall inventory health
- ✅ Product Analysis Report - Product details
- ✅ Stock Movement Report - Stock quantity tracking
- ✅ Inventory Alerts Report - Active alerts summary
- ✅ Supplier Performance Report - Supplier details
- ✅ Purchase Order Analytics - PO status breakdown
- ✅ Sales Order Analytics - SO status and revenue

## Database Schema

### Main Tables
- **products** - Product catalog
- **suppliers** - Supplier information
- **product_categories** - Product classification
- **stock_inventory** - Current stock levels
- **warehouse_locations** - Physical storage locations
- **purchase_orders** - PO headers
- **purchase_order_items** - PO line items
- **sales_orders** - Sales order headers
- **sales_order_items** - Sales order line items
- **inventory_alerts** - Automated alerts
- **stock_movement_history** - Audit trail

## Key Classes

### Models
- `Product` - Represents a product with pricing and reorder info
- `Supplier` - Represents a supplier with contact details
- `Stock` - Represents inventory stock levels
- `PurchaseOrder` - Represents a supplier purchase order
- `SalesOrder` - Represents a customer sales order
- `InventoryAlert` - Represents an inventory alert

### DAOs (Data Access Objects)
- `ProductDAO` - CRUD operations for products
- `SupplierDAO` - CRUD operations for suppliers
- `StockDAO` - Stock level management
- `PurchaseOrderDAO` - Purchase order operations
- `SalesOrderDAO` - Sales order operations
- `InventoryAlertDAO` - Alert management

### Services
- `InventoryService` - Inventory business logic
- `OrderService` - Order management logic
- `ReportService` - Report generation and analytics

## Usage Examples

### Starting the Application
```bash
java -cp target/warehouse-management-system-1.0.0.jar com.warehouse.app.WarehouseManagementApp
```

### Main Menu Operations

1. **Product Management** - Add, view, and manage products
2. **Inventory Management** - Track stock levels and movements
3. **Supplier Management** - Manage supplier information
4. **Purchase Orders** - Create and track supplier orders
5. **Sales Orders** - Process customer orders
6. **Inventory Alerts** - Monitor and resolve alerts
7. **Dashboard** - View real-time metrics
8. **Reports** - Generate comprehensive reports

## Performance Optimization

### Database Indexes
- SKU index for fast product lookup
- Product ID index on stock table
- Category and supplier indexes
- Status indexes for filtering
- Date indexes for range queries

### Query Optimization
- Prepared statements prevent SQL injection
- Left joins for efficient data retrieval
- Calculated fields (available_quantity) in database
- Batch operations for bulk updates

## Logging

Logs are configured via Logback. Check logs for:
- Database connection status
- CRUD operations
- Alert creation/resolution
- System errors and warnings

Configuration: `logback.xml` (in resources)

## Error Handling

- Database connection failures are caught and logged
- Invalid user inputs are validated
- SQL exceptions are handled gracefully
- File I/O operations wrapped in try-catch

## Future Enhancements

- Multi-warehouse support
- Barcode/QR code scanning
- Advanced analytics and forecasting
- Email/SMS notifications
- Web-based UI (Spring Boot + Angular/React)
- REST API endpoints
- Authentication & authorization
- Audit trails and compliance reports
- Integration with ERP systems

## Troubleshooting

### Database Connection Failed
```
Check:
1. MySQL server is running
2. Correct credentials in application.properties
3. Database name is "warehouse_db"
4. MySQL driver is in dependency
```

### Build Errors
```
Run: mvn clean compile
     mvn dependency:resolve
```

### Runtime Issues
```
1. Check logs for detailed error messages
2. Verify database schema is created
3. Check user permissions on database
4. Ensure Java version is 11+
```

## License

This project is created for educational purposes.

## Contact & Support

For issues, suggestions, or improvements, please refer to the documentation or review the source code.

## Contributors

- Development Team

---

**Version**: 1.0.0  
**Last Updated**: 2024  
**Status**: Production Ready

Project Name:
Inventory & Warehouse Management System

Database:
MySQL (warehouse_db)

Backend:
Java Spring Boot

Build Tool:
Maven

Run Command:
mvn spring-boot:run

Application URL:
http://localhost:8080

RUN COMMAND PROJECT
1. MySQL Start karo

Windows Services ya MySQL Workbench ke through ensure karo ki MySQL Server chal raha hai.

Check:

SELECT USER();

Agar result aa jaye to MySQL running hai.

2. VS Code me Project Open karo

Project folder open karo:

WarehouseManagement-with-web/WarehouseManagement

Jahan pom.xml file hai wahi root folder hona chahiye.

3. Terminal Open karo

VS Code terminal me pehle project root par jao:

cd WarehouseManagement

Check:

dir pom.xml

pom.xml dikhna chahiye.

4. Spring Boot Run karo
mvn spring-boot:run

Ya

mvn clean spring-boot:run
5. Success Message

Terminal me ye line aayegi:

Tomcat started on port(s): 8080
Started WarehouseManagementWebApp
6. Browser Open karo

Dashboard:

http://localhost:8080

APIs:

http://localhost:8080/api/products
http://localhost:8080/api/inventory
http://localhost:8080/api/suppliers