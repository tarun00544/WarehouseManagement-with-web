# Web Frontend Setup Guide

## 🌐 Bootstrap Web Interface Added!

Your Warehouse Management System now includes a **professional Bootstrap web interface** with REST APIs!

### What's New:

✅ **Spring Boot Web Application** - Full web server
✅ **Bootstrap 5 Frontend** - Modern, responsive UI
✅ **REST APIs** - Complete API endpoints
✅ **Real-time Dashboard** - Live metrics
✅ **Product Management** - Web-based interface
✅ **Inventory Tracking** - Visual inventory management
✅ **Responsive Design** - Works on mobile, tablet, desktop

---

## 🚀 Running the Web Version

### Step 1: Build the Project
```bash
cd WarehouseManagement
mvn clean package
```

### Step 2: Run the Spring Boot Application
```bash
java -jar target/warehouse-management-system-1.0.0.jar
```

Or using Maven:
```bash
mvn spring-boot:run
```

### Step 3: Open in Browser
```
http://localhost:8080
```

You'll see the professional Bootstrap dashboard!

---

## 📚 Web Pages Available

| Page | URL | Description |
|------|-----|-------------|
| Dashboard | `/` | Real-time metrics and overview |
| Products | `/products.html` | Manage products |
| Inventory | `/inventory.html` | Stock levels and tracking |
| Suppliers | `/suppliers.html` | Supplier management |
| Purchase Orders | `/purchase-orders.html` | PO management |
| Sales Orders | `/sales-orders.html` | Order management |
| Alerts | `/alerts.html` | Inventory alerts |
| Reports | `/reports.html` | Analytics reports |

---

## 🔌 REST API Endpoints

### Products
```
GET    /api/products              - Get all products
GET    /api/products/{id}         - Get product by ID
GET    /api/products/search?term= - Search products
POST   /api/products              - Add product
PUT    /api/products/{id}         - Update product
DELETE /api/products/{id}         - Delete product
```

### Inventory
```
GET    /api/inventory             - Get all stock
GET    /api/inventory/product/{id} - Get stock by product
GET    /api/inventory/low-stock   - Get low stock items
GET    /api/inventory/overstock   - Get overstock items
POST   /api/inventory/update/{id} - Update stock
GET    /api/inventory/total-value - Get total inventory value
```

### Suppliers
```
GET    /api/suppliers             - Get all suppliers
GET    /api/suppliers/{id}        - Get supplier
POST   /api/suppliers             - Add supplier
PUT    /api/suppliers/{id}        - Update supplier
DELETE /api/suppliers/{id}        - Delete supplier
```

### Alerts
```
GET    /api/alerts                - Get all active alerts
GET    /api/alerts/type/{type}    - Get alerts by type
GET    /api/alerts/severity/{sev} - Get alerts by severity
POST   /api/alerts/{id}/resolve   - Resolve alert
GET    /api/alerts/count          - Get alert count
```

---

## 🎨 Features of the Web Interface

### Dashboard
- 📊 Real-time metrics cards
- 📈 Total products count
- 💰 Inventory value in rupees
- 👥 Active suppliers count
- ⚠️ Active alerts count
- 📋 Low stock items list
- 🔔 Recent alerts display
- ⚡ Quick action buttons

### Product Management
- ➕ Add new products
- 🔍 Search products by name/SKU
- ✏️ Edit product details
- 🗑️ Delete products
- 📊 Product listing table
- 💵 Price display
- 📦 Stock information

### Responsive Design
- ✅ Mobile friendly
- ✅ Tablet optimized
- ✅ Desktop full features
- ✅ Touch-friendly buttons
- ✅ Accessible navigation

---

## 🛠️ Technology Stack (Web)

| Technology | Version | Purpose |
|-----------|---------|---------|
| Spring Boot | 2.7.14 | Web framework |
| Bootstrap | 5.3.0 | UI framework |
| Axios | 1.4.0 | HTTP client |
| Font Awesome | 6.4.0 | Icons |
| MySQL | 8.0+ | Database |
| Java | 11+ | Backend |

---

## 📝 Configuration

### application.yml
Located at: `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/warehouse_db
    username: root
    password: YOUR_PASSWORD

server:
  port: 8080
```

**Before running, update:**
- `username` - Your MySQL username
- `password` - Your MySQL password
- `url` - Database URL if different

---

## 🌐 Browser Compatibility

✅ Chrome 90+
✅ Firefox 88+
✅ Safari 14+
✅ Edge 90+
✅ Mobile browsers

---

## 📱 Features by Page

### Dashboard (`/`)
- Metrics cards with real-time data
- Low stock items widget
- Recent alerts widget
- Quick action buttons
- Auto-refresh every 30 seconds

### Products (`/products.html`)
- List all products in table
- Search by name or SKU
- Add new product modal
- Edit product details
- Delete products
- Status badges

### Inventory (`/inventory.html`)
- View all stock levels
- Filter by low stock
- Filter by overstock
- Update stock quantities
- Inventory value calculation
- Location tracking

### Suppliers (`/suppliers.html`)
- List all suppliers
- Contact information display
- Add new supplier
- Update supplier details
- Deactivate supplier
- Performance metrics

### Alerts (`/alerts.html`)
- Active alerts list
- Filter by severity
- Filter by alert type
- Resolve alerts
- Alert count display

---

## 🔐 Default Credentials

No authentication required for the web interface in this version.

For production, add Spring Security:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

---

## 🐛 Troubleshooting Web Version

### Problem: Port 8080 already in use
```
Solution: Change port in application.yml
server:
  port: 8081
```

### Problem: Cannot connect to database
```
Solution: 
1. Check MySQL is running
2. Verify credentials in application.yml
3. Run: mysql -u root -p -e "USE warehouse_db;"
```

### Problem: CSS/JS files not loading
```
Solution:
1. Ensure files are in src/main/resources/static/
2. Clear browser cache (Ctrl+Shift+Delete)
3. Restart Spring Boot application
```

### Problem: API endpoints returning 404
```
Solution:
1. Ensure controllers are in correct package
2. Check @RequestMapping paths
3. Verify Spring Boot is running on localhost:8080
```

---

## 📊 REST API Usage Examples

### Get all products (JavaScript)
```javascript
axios.get('/api/products')
    .then(response => console.log(response.data))
    .catch(error => console.error(error));
```

### Add new product
```javascript
const product = {
    productName: "Laptop",
    sku: "LAP-001",
    categoryId: 1,
    unitPrice: 999.99,
    reorderLevel: 10,
    supplierId: 1
};

axios.post('/api/products', product)
    .then(response => console.log(response.data))
    .catch(error => console.error(error));
```

### Update stock
```javascript
axios.post('/api/inventory/update/1?quantity=50&type=INBOUND')
    .then(response => console.log(response.data))
    .catch(error => console.error(error));
```

---

## 🎯 Next Steps

1. ✅ Build the project: `mvn clean package`
2. ✅ Run Spring Boot: `java -jar target/warehouse-management-system-1.0.0.jar`
3. ✅ Open browser: `http://localhost:8080`
4. ✅ Explore the dashboard
5. ✅ Test all CRUD operations
6. ✅ Generate reports
7. ✅ Check alerts

---

## 📞 API Documentation

Full API documentation is available when running the application.

Access Swagger UI (if added):
```
http://localhost:8080/swagger-ui.html
```

To enable Swagger, add dependency:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.0</version>
</dependency>
```

---

## 🚀 Performance Tips

1. **Enable caching**: Add `@Cacheable` to frequently accessed methods
2. **Use pagination**: Add pagination to large data sets
3. **Index database**: Add indexes to frequently searched columns
4. **Minify CSS/JS**: For production, minify static assets
5. **Enable gzip**: Compress HTTP responses

---

**Enjoy your new web-based Warehouse Management System!** 🎉

For console version, run: `java -cp target/*.jar com.warehouse.app.WarehouseManagementApp`
