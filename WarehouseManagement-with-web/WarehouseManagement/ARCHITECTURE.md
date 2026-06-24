# Project Architecture & Structure

## System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    USER INTERFACE                            │
│          Menu-Driven Console Application                     │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                  APPLICATION LAYER                           │
│         WarehouseManagementApp.java (Main)                  │
│        Handles user input and menu navigation                │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                  SERVICE LAYER                               │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ InventoryService  │ OrderService  │ ReportService  │    │
│  │  Business Logic   │ Order Logic   │ Analytics      │    │
│  └─────────────────────────────────────────────────────┘    │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                   DAO LAYER                                  │
│  ┌──────────────────────────────────────────────────┐       │
│  │ProductDAO │ SupplierDAO │ StockDAO │ AlertDAO    │       │
│  │PurchaseOrderDAO │ SalesOrderDAO                  │       │
│  │Data Access & CRUD Operations                    │       │
│  └──────────────────────────────────────────────────┘       │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                 MODEL LAYER                                  │
│  Product │ Supplier │ Stock │ Purchase/SalesOrder │ Alert   │
│           Entity Classes (POJOs)                             │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                DATABASE LAYER                                │
│         DatabaseConfig.java (Connection Management)         │
│              JDBC → MySQL Database                           │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                  MYSQL DATABASE                              │
│  warehouse_db with 10+ tables and relationships             │
└─────────────────────────────────────────────────────────────┘
```

## Directory Structure

```
WarehouseManagement/
│
├── pom.xml
│   └─ Maven configuration, dependencies, build plugins
│
├── database_schema.sql
│   └─ Complete database structure with sample data
│
├── README.md
│   └─ Full project documentation
│
├── QUICKSTART.md
│   └─ Quick setup and getting started guide
│
├── src/main/
│   │
│   ├── java/com/warehouse/
│   │   │
│   │   ├── app/
│   │   │   └── WarehouseManagementApp.java
│   │   │       • Main application entry point
│   │   │       • Menu-driven interface
│   │   │       • User input handling
│   │   │       • Report generation
│   │   │
│   │   ├── dao/
│   │   │   ├── ProductDAO.java
│   │   │   │   • Add/update/delete products
│   │   │   │   • Search by SKU or name
│   │   │   │   • Category filtering
│   │   │   │   • Product count analytics
│   │   │   │
│   │   │   ├── SupplierDAO.java
│   │   │   │   • Supplier CRUD operations
│   │   │   │   • Status management
│   │   │   │   • Supplier analytics
│   │   │   │
│   │   │   ├── StockDAO.java
│   │   │   │   • Stock level updates
│   │   │   │   • Reserved quantity tracking
│   │   │   │   • Low stock identification
│   │   │   │   • Inventory valuation
│   │   │   │
│   │   │   ├── PurchaseOrderDAO.java
│   │   │   │   • Purchase order creation
│   │   │   │   • Status updates
│   │   │   │   • Order filtering
│   │   │   │   • Cost calculations
│   │   │   │
│   │   │   ├── SalesOrderDAO.java
│   │   │   │   • Sales order creation
│   │   │   │   • Customer management
│   │   │   │   • Revenue tracking
│   │   │   │   • Order status management
│   │   │   │
│   │   │   └── InventoryAlertDAO.java
│   │   │       • Alert creation
│   │   │       • Alert resolution
│   │   │       • Severity filtering
│   │   │       • Alert statistics
│   │   │
│   │   ├── model/
│   │   │   ├── Product.java
│   │   │   │   • Product information
│   │   │   │   • Pricing and reorder levels
│   │   │   │   • Supplier reference
│   │   │   │
│   │   │   ├── Supplier.java
│   │   │   │   • Supplier details
│   │   │   │   • Contact information
│   │   │   │   • Payment terms
│   │   │   │   • Status tracking
│   │   │   │
│   │   │   ├── Stock.java
│   │   │   │   • Current/reserved quantities
│   │   │   │   • Calculated available quantity
│   │   │   │   • Location tracking
│   │   │   │   • Last check timestamp
│   │   │   │
│   │   │   ├── PurchaseOrder.java
│   │   │   │   • Purchase order details
│   │   │   │   • Supplier reference
│   │   │   │   • Status and dates
│   │   │   │   • Order totals
│   │   │   │
│   │   │   ├── SalesOrder.java
│   │   │   │   • Sales order details
│   │   │   │   • Customer information
│   │   │   │   • Delivery tracking
│   │   │   │   • Order value
│   │   │   │
│   │   │   └── InventoryAlert.java
│   │   │       • Alert information
│   │   │       • Severity levels
│   │   │       • Resolution tracking
│   │   │
│   │   ├── service/
│   │   │   ├── InventoryService.java
│   │   │   │   • Stock updates
│   │   │   │   • Automatic alert generation
│   │   │   │   • Low/overstock detection
│   │   │   │   • Inventory summary
│   │   │   │
│   │   │   ├── OrderService.java
│   │   │   │   • Purchase order management
│   │   │   │   • Sales order management
│   │   │   │   • Order status updates
│   │   │   │   • Revenue calculations
│   │   │   │
│   │   │   └── ReportService.java
│   │   │       • Inventory reports
│   │   │       • Product analysis
│   │   │       • Stock movements
│   │   │       • Supplier performance
│   │   │       • Order analytics
│   │   │       • Revenue reporting
│   │   │
│   │   └── util/
│   │       └── DatabaseConfig.java
│   │           • Connection pooling
│   │           • Configuration loading
│   │           • Connection testing
│   │           • Error handling
│   │
│   └── resources/
│       └── application.properties
│           • Database URL
│           • Username/Password
│           • Driver configuration
│           • Connection pool size
│
└── target/
    ├── warehouse-management-system-1.0.0.jar
    └── (compiled classes and resources)
```

## Data Flow

### Example: Adding Stock & Creating Alert

```
User Input (Main Menu)
       │
       ├─→ WarehouseManagementApp
       │    └─→ updateStockQuantity()
       │         │
       │         └─→ InventoryService
       │              └─→ updateStock(productId, qty, type)
       │                   │
       │                   ├─→ StockDAO.updateStockQuantity()
       │                   │    └─→ SQL UPDATE stock_inventory
       │                   │         └─→ MySQL Database
       │                   │
       │                   └─→ checkAndCreateAlerts()
       │                        │
       │                        ├─→ ProductDAO.getProductById()
       │                        │
       │                        ├─→ StockDAO.getStockByProductId()
       │                        │
       │                        └─→ InventoryAlertDAO.addAlert()
       │                             └─→ SQL INSERT inventory_alerts
       │                                  └─→ MySQL Database
       │
       └─→ Console Output
            "✓ Stock updated successfully!"
```

## Database Relationships

```
Products ────┐
  (1)        │ (Many)
             │
    └────────┴─→ Stock_Inventory
             
Suppliers ───────→ Products
  (1)       (Many)

Purchase_Orders ──────┐
  (1)                 │ (Many)
                      │
         └────────────┴─→ Purchase_Order_Items
                               │
                        (Many)  │
                                └─→ Products

Sales_Orders ────────┐
  (1)                │ (Many)
                     │
         └───────────┴─→ Sales_Order_Items
                             │
                      (Many)  │
                              └─→ Products

Products ─────────────→ Inventory_Alerts
  (1)         (Many)
```

## Key Design Patterns Used

### 1. **DAO Pattern (Data Access Object)**
- Separates business logic from database access
- Each entity has dedicated DAO class
- Encapsulates SQL queries
- Easy to switch database implementation

### 2. **Service Layer Pattern**
- Business logic separated from DAOs
- Handles complex operations
- Manages transactions
- Coordinates multiple DAOs

### 3. **Model/Entity Pattern**
- POJOs (Plain Old Java Objects) for entities
- Getters/setters for properties
- toString() for debugging
- Encapsulation of data

### 4. **Singleton Pattern**
- DatabaseConfig class uses static initialization
- Ensures single database connection pool
- Shared across all DAOs

### 5. **Factory Pattern**
- Services create DAO instances
- DAOs create model objects from ResultSet

## Exception Handling Strategy

```
SQLException (Database errors)
    │
    ├─→ Logged to file
    ├─→ Message displayed to user
    └─→ Operation returns false/null
    
NumberFormatException (Input validation)
    │
    └─→ Returns default value (0, ZERO, null)
    
Generic Exception (Unexpected errors)
    │
    └─→ Logged and caught gracefully
```

## Caching Strategy

Currently: **No explicit caching** (Direct database queries)

Future improvements:
- Product cache (read-heavy)
- Stock cache with TTL
- Alert frequency limiting

## Security Considerations

✅ **Implemented:**
- Prepared statements (prevent SQL injection)
- Input validation
- Logging of operations
- Connection pooling

🔒 **Recommended for Production:**
- Authentication & authorization
- Encrypted database passwords
- Role-based access control
- Audit trails for all operations
- HTTPS for API (if REST added)
- Database encryption at rest

## Performance Optimization

### Database Level
- Indexes on frequently queried columns
- Calculated fields in database
- LIMIT clauses for large result sets
- Efficient JOIN queries

### Application Level
- Prepared statements for reusable queries
- Connection pooling
- Lazy loading where applicable
- Batch operations for bulk updates

## Scalability Considerations

**Current Implementation:**
- Single JVM process
- Single MySQL database
- Console-based interface

**For Scaling:**
1. **Horizontal**: Add web UI with Spring Boot
2. **Vertical**: Optimize database queries
3. **Database**: Implement read replicas
4. **Cache**: Add Redis for hot data
5. **APIs**: REST endpoints for integration

## Testing Strategy (Recommended)

```
Unit Tests:
  └─ DAOTest (Mock database)
  └─ ServiceTest (Mock DAOs)
  └─ ModelTest (Entity validation)

Integration Tests:
  └─ DAO integration with test database
  └─ Service layer with DAOs

End-to-End Tests:
  └─ Full workflow testing
  └─ Report generation verification
```

## Deployment Checklist

- ✅ Code reviewed
- ✅ Tested locally
- ✅ Database schema verified
- ✅ Configuration updated
- ✅ Dependencies resolved
- ✅ JAR built successfully
- ✅ Java environment set
- ✅ Database backups taken
- ✅ Logging configured
- ✅ Documentation updated

## Version History

**v1.0.0** - Initial Release
- Product management
- Stock inventory tracking
- Supplier management
- Purchase & sales orders
- Inventory alerts
- Reports & analytics
- Dashboard

---

This architecture is designed for:
- **Maintainability**: Clear separation of concerns
- **Extensibility**: Easy to add new features
- **Testability**: Each layer can be tested independently
- **Scalability**: Ready for future enhancements
