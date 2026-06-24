# Quick Start Guide - Warehouse Management System

## Prerequisites Check

Verify you have installed:
- Java JDK 11+ : `java -version`
- Maven 3.6+ : `mvn -version`
- MySQL 8.0+ : `mysql --version`

## Step 1: Database Setup (5 minutes)

### Option A: Using Command Line
```bash
# Connect to MySQL
mysql -u root -p

# Run the schema file
source database_schema.sql;

# Verify
USE warehouse_db;
SHOW TABLES;
```

### Option B: Using MySQL Workbench
1. Open MySQL Workbench
2. File → Open SQL Script
3. Select `database_schema.sql`
4. Execute (Ctrl+Enter)

### Verify Database
```sql
SELECT COUNT(*) FROM products;  -- Should return 10
SELECT COUNT(*) FROM suppliers; -- Should return 3
SELECT COUNT(*) FROM stock_inventory; -- Should return 10
```

## Step 2: Configure Connection (2 minutes)

Edit `src/main/resources/application.properties`:

```properties
# MySQL Connection
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/warehouse_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=root
db.password=YOUR_PASSWORD
```

Replace `YOUR_PASSWORD` with your MySQL root password.

## Step 3: Build Project (3 minutes)

```bash
cd WarehouseManagement
mvn clean install
```

Expected output:
```
BUILD SUCCESS
Total time: XX.XXs
```

## Step 4: Run Application (1 minute)

```bash
# Option 1: Using JAR file
java -jar target/warehouse-management-system-1.0.0.jar

# Option 2: Using Maven
mvn exec:java -Dexec.mainClass="com.warehouse.app.WarehouseManagementApp"
```

## First Steps in Application

1. **Main Menu** appears with 9 options
2. **Test Database Connection** - System automatically verifies
3. **Try These Actions**:
   - Option 1: View all products (should show 10 products)
   - Option 2: Check inventory (shows sample stock data)
   - Option 3: View suppliers (shows 3 suppliers)
   - Option 7: Dashboard (shows real-time metrics)
   - Option 8: Generate reports

## Sample Data Included

The database includes pre-loaded data:

### Products (10 items)
- Laptops, Monitors, Keyboards, Mice, USB Cables
- Industrial Drills, Tool Sets
- Packaging Tape, Cardboard Boxes
- Office Desks

### Suppliers (3 items)
- TechSupply Inc
- Global Logistics
- Electronics Wholesale

### Stock Levels
- Electronics: 250+ units
- Hardware: 63+ units
- Packaging: 1250+ units
- Office: 22+ units

## Common Operations

### Add a New Product
1. Main Menu → Option 1 (Product Management)
2. Option 1 (Add Product)
3. Enter details and confirm

### Create a Purchase Order
1. Main Menu → Option 4 (Purchase Orders)
2. Option 1 (Create Purchase Order)
3. Enter PO details, supplier ID, delivery date

### Check Inventory
1. Main Menu → Option 2 (Stock/Inventory Management)
2. Option 1 (View All Stock)
3. See current quantities and availability

### View Dashboard
1. Main Menu → Option 7 (Dashboard & Analytics)
2. See real-time inventory and order metrics

### Generate Report
1. Main Menu → Option 8 (Reports)
2. Select report type (Inventory, Product, Analytics, etc.)

## Troubleshooting

### Problem: "Connection refused"
**Solution**: 
- Ensure MySQL is running: `sudo service mysql start`
- Check credentials in application.properties
- Verify database_db exists: `mysql -u root -p -e "USE warehouse_db;"`

### Problem: "Driver not found"
**Solution**:
- Run `mvn dependency:resolve`
- Rebuild with `mvn clean install`

### Problem: "Table doesn't exist"
**Solution**:
- Run database_schema.sql again
- Ensure you're using warehouse_db database

### Problem: Build fails with "Compilation error"
**Solution**:
- Java version must be 11+: `java -version`
- Update Java: Download from oracle.com/java
- Clean rebuild: `mvn clean compile`

## Environment Variables (Optional)

Set for faster startup:
```bash
export JAVA_HOME=/path/to/java11
export M2_HOME=/path/to/maven
export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$PATH
```

## Default Credentials

### MySQL
- Username: root
- Password: (as configured)

### Application
- No authentication required for default setup
- All operations use SYSTEM user

## File Locations

| File | Purpose |
|------|---------|
| `pom.xml` | Maven dependencies and build config |
| `database_schema.sql` | Database structure and sample data |
| `src/main/resources/application.properties` | Database connection settings |
| `src/main/java/com/warehouse/app/WarehouseManagementApp.java` | Main application |
| `README.md` | Full documentation |

## Project Size

```
Project Structure:
- Source code: ~3500 lines
- Database schema: ~400 lines
- Documentation: ~300 lines
- Total files: 20+
```

## Performance Notes

- Database queries use prepared statements (prevents SQL injection)
- Indexes on key columns for fast lookups
- Connection pooling for efficient database access
- Batch operations for bulk updates

## Next Steps

After successful setup:
1. **Explore Sample Data** - View products, stock, suppliers
2. **Create Test Orders** - Add purchase and sales orders
3. **Generate Reports** - See analytics and metrics
4. **Add Your Data** - Import real business data
5. **Customize** - Modify code for specific needs

## Support Resources

- **Full Documentation**: See README.md
- **Source Code**: Well-commented Java classes
- **Database**: SQL comments explain table purposes
- **Logging**: Check application logs for details

## Development Tips

### Adding a New Feature
1. Create model class (extends existing patterns)
2. Create DAO for database operations
3. Create service for business logic
4. Add menu option in WarehouseManagementApp.java

### Database Backup
```bash
mysqldump -u root -p warehouse_db > backup.sql
```

### Database Restore
```bash
mysql -u root -p warehouse_db < backup.sql
```

## Estimated Setup Time: 15 minutes total

- Prerequisites check: 2 min
- Database setup: 5 min
- Configuration: 2 min
- Build: 3 min
- First run: 1 min
- Testing: 2 min

---

**Ready to use!** 🚀

If you encounter any issues, check the Troubleshooting section or review README.md for detailed documentation.
