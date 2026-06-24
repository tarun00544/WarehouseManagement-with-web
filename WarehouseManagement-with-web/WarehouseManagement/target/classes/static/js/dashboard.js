// Dashboard JavaScript

/**
 * Load dashboard data
 */
async function loadDashboardData() {
    try {
        // Load metrics
        loadMetrics();
        
        // Load low stock items
        loadLowStockItems();
        
        // Load alerts
        loadAlerts();
    } catch (error) {
        console.error('Error loading dashboard:', error);
        showToast('Error loading dashboard data', 'danger');
    }
}

/**
 * Load dashboard metrics
 */
async function loadMetrics() {
    try {
        // Get total products
        const productsData = await ProductAPI.getCount();
        if (productsData.success) {
            document.getElementById('totalProducts').textContent = productsData.count;
        }

        // Get inventory value
        const valueData = await InventoryAPI.getTotalValue();
        if (valueData.success) {
            document.getElementById('totalValue').textContent = formatCurrency(valueData.totalValue || 0);
        }

        // Get supplier count
        const suppliersData = await SupplierAPI.getCount();
        if (suppliersData.success) {
            document.getElementById('totalSuppliers').textContent = suppliersData.count;
        }

        // Get alert count
        const alertsData = await AlertAPI.getCount();
        if (alertsData.success) {
            document.getElementById('activeAlerts').textContent = alertsData.count;
            document.getElementById('alertBadge').textContent = alertsData.count;
            
            // Show critical count if available
            const criticalData = await AlertAPI.getCriticalCount();
            if (criticalData.success && criticalData.count > 0) {
                document.getElementById('activeAlerts').classList.add('text-danger');
            }
        }
    } catch (error) {
        console.error('Error loading metrics:', error);
    }
}

/**
 * Load low stock items
 */
async function loadLowStockItems() {
    const container = document.getElementById('lowStockList');
    if (!container) return;

    try {
        showSpinner('lowStockList');
        const data = await InventoryAPI.getLowStock(100);

        if (data.success && data.data && data.data.length > 0) {
            let html = '<ul class="list-group list-group-flush">';
            data.data.slice(0, 5).forEach(item => {
                html += `
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                            <strong>${item.productName}</strong>
                            <br>
                            <small class="text-muted">Available: ${item.availableQuantity}</small>
                        </div>
                        <span class="badge bg-warning rounded-pill">${item.availableQuantity}</span>
                    </li>
                `;
            });
            html += '</ul>';
            if (data.data.length > 5) {
                html += '<p class="text-muted mt-2"><small><a href="inventory.html?view=low-stock">View all low stock items →</a></small></p>';
            }
            container.innerHTML = html;
        } else {
            container.innerHTML = '<p class="text-success text-center py-3"><i class="fas fa-check-circle"></i> No low stock items</p>';
        }
    } catch (error) {
        console.error('Error loading low stock items:', error);
        container.innerHTML = '<p class="text-danger">Error loading data</p>';
    }
}

/**
 * Load alerts
 */
async function loadAlerts() {
    const container = document.getElementById('alertsList');
    if (!container) return;

    try {
        showSpinner('alertsList');
        const data = await AlertAPI.getAll();

        if (data.success && data.data && data.data.length > 0) {
            let html = '<ul class="list-group list-group-flush">';
            data.data.slice(0, 5).forEach(alert => {
                const severityClass = {
                    'CRITICAL': 'danger',
                    'HIGH': 'warning',
                    'MEDIUM': 'info',
                    'LOW': 'secondary'
                }[alert.severity] || 'secondary';

                html += `
                    <li class="list-group-item d-flex justify-content-between align-items-start">
                        <div class="flex-grow-1">
                            <strong>${alert.productName}</strong>
                            <br>
                            <small class="text-muted">${alert.alertMessage}</small>
                        </div>
                        <span class="badge bg-${severityClass}">${alert.severity}</span>
                    </li>
                `;
            });
            html += '</ul>';
            if (data.data.length > 5) {
                html += '<p class="text-muted mt-2"><small><a href="alerts.html">View all alerts →</a></small></p>';
            }
            container.innerHTML = html;
        } else {
            container.innerHTML = '<p class="text-success text-center py-3"><i class="fas fa-check-circle"></i> No active alerts</p>';
        }
    } catch (error) {
        console.error('Error loading alerts:', error);
        container.innerHTML = '<p class="text-danger">Error loading data</p>';
    }
}

/**
 * Refresh dashboard data every 30 seconds
 */
function startAutoRefresh() {
    setInterval(() => {
        loadDashboardData();
    }, 30000);
}

// Load dashboard on page load
document.addEventListener('DOMContentLoaded', () => {
    loadDashboardData();
    startAutoRefresh();
});
