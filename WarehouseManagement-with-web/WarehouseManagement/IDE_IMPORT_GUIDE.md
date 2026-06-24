# How to Import the Warehouse Management System into Your IDE

## Option 1: IntelliJ IDEA (Recommended)

### Step 1: Copy the Project Folder
1. Copy the entire `WarehouseManagement` folder to your desired location
2. Remember the path (e.g., `C:\Projects\WarehouseManagement` or `/home/user/Projects/WarehouseManagement`)

### Step 2: Open IntelliJ IDEA
1. Launch IntelliJ IDEA
2. Click **File** → **Open**
3. Navigate to the `WarehouseManagement` folder
4. Select the folder and click **Open**

### Step 3: Configure the Project
1. IntelliJ will detect it's a Maven project automatically
2. You'll see a notification: "Maven projects detected"
3. Click **Load Maven Project** or **Enable Auto-Import**
4. Wait for Maven to download dependencies (check console at bottom)

### Step 4: Set Java Version
1. Go to **File** → **Project Structure** (or Ctrl+Alt+Shift+S)
2. Under **Project**:
   - Set **SDK** to Java 11 or higher
   - Set **Language level** to 11
3. Under **Modules** → **Sources**:
   - Verify source folder is `src/main/java`
   - Verify resources folder is `src/main/resources`
4. Click **Apply** → **OK**

### Step 5: Configure Database
1. Open `src/main/resources/application.properties`
2. Edit database credentials:
   ```properties
   db.username=root
   db.password=YOUR_MYSQL_PASSWORD
   ```
3. Save the file (Ctrl+S)

### Step 6: Run the Project
**Option A - Run Main Class:**
1. Open `src/main/java/com/warehouse/app/WarehouseManagementApp.java`
2. Click the green **Run** button next to `public static void main()`
3. Or right-click → **Run 'WarehouseManagementApp.main()'**

**Option B - Run via Maven:**
1. Open **Maven** panel on the right
2. Expand **warehouse-management-system**
3. Right-click **Plugins** → **exec** → **exec:java**

**Option C - Build JAR First:**
1. Open **Maven** panel
2. Right-click root project
3. Select **Run Maven Goal**
4. Type: `clean package`
5. Click **Run**
6. Then run the JAR file

### Troubleshooting IntelliJ

**Issue: "SDK not configured"**
```
Solution:
1. File → Project Structure
2. Project → SDK → Add SDK
3. Select your Java 11+ installation
4. Click OK
```

**Issue: "Maven dependencies not downloaded"**
```
Solution:
1. Right-click pom.xml
2. Select "Run Maven" → "Reimport"
3. Or: Maven panel → Reload All Maven Projects
```

**Issue: "Cannot find symbol"**
```
Solution:
1. File → Invalidate Caches
2. Check Mark Dirty
3. Click "Invalidate and Restart"
4. IntelliJ will reindex the project
```

---

## Option 2: Eclipse IDE

### Step 1: Install Maven Plugin
1. Open Eclipse
2. Go to **Help** → **Eclipse Marketplace**
3. Search for "M2E" or "Maven"
4. Install **m2e - Maven Integration for Eclipse**
5. Restart Eclipse

### Step 2: Import Project
1. **File** → **Import**
2. Select **Maven** → **Existing Maven Projects**
3. Click **Next**
4. Click **Browse** and select the `WarehouseManagement` folder
5. Check the box next to `pom.xml`
6. Click **Finish**
7. Eclipse will download dependencies automatically

### Step 3: Configure Java Compiler
1. Right-click the project → **Properties**
2. Go to **Java Compiler**
3. Set **Compiler compliance level** to 11
4. Set **Generated .class files compatibility** to 11
5. Click **Apply and Close**

### Step 4: Configure Database
1. Open `src/main/resources/application.properties`
2. Update MySQL credentials:
   ```properties
   db.username=root
   db.password=YOUR_PASSWORD
   ```
3. Save (Ctrl+S)

### Step 5: Run the Application
**Option A - Run Main Class:**
1. Open `WarehouseManagementApp.java`
2. Right-click → **Run As** → **Java Application**

**Option B - Run Maven Build:**
1. Right-click project
2. **Run As** → **Maven Build...**
3. In **Goals**, type: `clean package`
4. Click **Run**

### Step 6: Fix Build Path (if needed)
1. Right-click project → **Build Path** → **Configure Build Path**
2. Check **Libraries** tab:
   - JRE System Library should be Java 11+
   - M2_REPO library should be present
3. Click **Apply and Close**

### Troubleshooting Eclipse

**Issue: "Unresolved compilation error"**
```
Solution:
1. Right-click project
2. Maven → Update Project
3. Or: Alt+F5
```

**Issue: "JAR files missing from classpath"**
```
Solution:
1. Right-click project → Maven → Update Dependencies
2. Wait for download to complete
3. Project → Clean (removes build artifacts)
```

---

## Option 3: Visual Studio Code (VS Code)

### Step 1: Install Extensions
1. Open VS Code
2. Go to **Extensions** (Ctrl+Shift+X)
3. Install these extensions:
   - **Extension Pack for Java** (by Microsoft)
   - **Maven for Java** (by Microsoft)
   - **Project Manager for Java** (by Microsoft)
4. Restart VS Code

### Step 2: Open the Project
1. **File** → **Open Folder** (Ctrl+K Ctrl+O)
2. Navigate to `WarehouseManagement` folder
3. Click **Select Folder**
4. VS Code will detect it's a Maven project
5. Wait for extension initialization

### Step 3: Configure Java Runtime
1. Open **Command Palette** (Ctrl+Shift+P)
2. Type: **Java: Configure Java Runtime**
3. Select Java 11 or higher
4. Or: Click **Java Runtime** in status bar (bottom right)

### Step 4: Edit Database Configuration
1. Open `src/main/resources/application.properties`
2. Update credentials:
   ```properties
   db.username=root
   db.password=YOUR_PASSWORD
   ```
3. Save (Ctrl+S)

### Step 5: Run the Application
**Option A - Run Main Class:**
1. Open `src/main/java/com/warehouse/app/WarehouseManagementApp.java`
2. Click **Run** link above `public static void main()`
3. Or press Ctrl+F5

**Option B - Run via Maven:**
1. Open **Command Palette** (Ctrl+Shift+P)
2. Type: **Maven: Run Maven Command**
3. Select your project
4. Enter goal: `clean package`
5. Press Enter

**Option C - Open Terminal & Run:**
```bash
cd WarehouseManagement
mvn clean install
java -jar target/warehouse-management-system-1.0.0.jar
```

### Step 6: Configure Launch Settings (Optional)
1. Create `.vscode/launch.json`:
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "name": "Warehouse Management App",
            "type": "java",
            "name": "Launch WarehouseManagementApp",
            "request": "launch",
            "mainClass": "com.warehouse.app.WarehouseManagementApp",
            "projectName": "warehouse-management-system",
            "cwd": "${workspaceFolder}",
            "console": "integratedTerminal",
            "args": ""
        }
    ]
}
```

### Troubleshooting VS Code

**Issue: "Cannot find Java installation"**
```
Solution:
1. Install Java 11+ from oracle.com/java
2. Ctrl+Shift+P → Java: Configure Java Runtime
3. Select the installed Java version
```

**Issue: "Main class not found"**
```
Solution:
1. Make sure source files are in src/main/java
2. Rebuild: Ctrl+Shift+P → Maven: Rebuild
3. Check the Run configuration
```

---

## General Setup Across All IDEs

### After Importing, Always Do:

#### 1. Download Dependencies
```bash
cd WarehouseManagement
mvn dependency:resolve
```

#### 2. Create Database
```bash
mysql -u root -p < database_schema.sql
```

#### 3. Build the Project
```bash
mvn clean compile
```

#### 4. Test the Build
```bash
mvn test
```

---

## Alternative: Command Line Setup (Works with Any IDE)

### Step 1: Open Terminal/Command Prompt
```bash
cd path/to/WarehouseManagement
```

### Step 2: Download Dependencies
```bash
mvn clean install
```

### Step 3: Build JAR
```bash
mvn package
```

### Step 4: Run
```bash
java -jar target/warehouse-management-system-1.0.0.jar
```

### Then Open in IDE
1. Open your IDE
2. File → Import → Maven Project
3. Select the `WarehouseManagement` folder
4. IDE will recognize it's already built

---

## Quick Comparison of IDEs

| Feature | IntelliJ | Eclipse | VS Code |
|---------|----------|---------|---------|
| Ease of Import | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐ |
| Maven Support | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| Performance | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| Resource Usage | ⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| Debugging | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| Free Version | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |

**Recommendation**: **IntelliJ IDEA** for best experience, **Eclipse** for free alternative, **VS Code** for lightweight option

---

## Final Checklist Before Running

- ✅ Java 11+ installed
- ✅ Maven installed
- ✅ MySQL running
- ✅ Database created (`warehouse_db`)
- ✅ `application.properties` updated with correct credentials
- ✅ All dependencies downloaded
- ✅ Project compiled successfully
- ✅ No red error indicators

---

## Typical Import Time

| Task | Time |
|------|------|
| Copy project folder | 1 min |
| Open in IDE | 1 min |
| Maven dependency download | 3-5 min |
| First build | 2-3 min |
| **Total** | **7-10 min** |

---

## If You Have Issues

### Check These Files First:
1. **pom.xml** - Maven configuration
2. **application.properties** - Database settings
3. **.classpath** (Eclipse) - Build path
4. **.project** (Eclipse) - Project configuration

### Common Issues & Quick Fixes:

```
Issue: "Module not found"
Fix: mvn clean install

Issue: "Connection refused"
Fix: Check MySQL is running, verify credentials

Issue: "Main class not found"
Fix: Rebuild project, check source folder configuration

Issue: "Symbol not found"
Fix: Invalidate cache/restart IDE, or mvn clean compile

Issue: "Port already in use"
Fix: Change port in application.properties or kill process
```

---

**You're all set!** 🚀 Your IDE is now ready to run the Warehouse Management System.
