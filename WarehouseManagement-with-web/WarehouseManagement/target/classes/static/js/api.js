// API Configuration and Helper Functions

const API_BASE_URL = '/api';

/**
 * Generic API call function
 */
async function apiCall(endpoint, method = 'GET', data = null) {
    try {
        const config = {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            }
        };

        if (data) {
            config.data = JSON.stringify(data);
        }

        const response = await axios(`${API_BASE_URL}${endpoint}`, config);
        return response.data;
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// ============== PRODUCT API ==============

const ProductAPI = {
    getAll: () => apiCall('/products'),
    getById: (id) => apiCall(`/products/${id}`),
    search: (term) => apiCall(`/products/search?term=${term}`),
    add: (product) => apiCall('/products', 'POST', product),
    update: (id, product) => apiCall(`/products/${id}`, 'PUT', product),
    delete: (id) => apiCall(`/products/${id}`, 'DELETE'),
    getCount: () => apiCall('/products/count')
};

// ============== INVENTORY API ==============

const InventoryAPI = {
    getAll: () => apiCall('/inventory'),
    getByProduct: (productId) => apiCall(`/inventory/product/${productId}`),
    getLowStock: (threshold = 100) => apiCall(`/inventory/low-stock?threshold=${threshold}`),
    getOverstock: () => apiCall('/inventory/overstock'),
    update: (productId, quantity, type) => 
        apiCall(`/inventory/update/${productId}?quantity=${quantity}&type=${type}`, 'POST'),
    getTotalValue: () => apiCall('/inventory/total-value'),
    getTotalQuantity: () => apiCall('/inventory/total-quantity')
};

// ============== SUPPLIER API ==============

const SupplierAPI = {
    getAll: () => apiCall('/suppliers'),
    getById: (id) => apiCall(`/suppliers/${id}`),
    add: (supplier) => apiCall('/suppliers', 'POST', supplier),
    update: (id, supplier) => apiCall(`/suppliers/${id}`, 'PUT', supplier),
    delete: (id) => apiCall(`/suppliers/${id}`, 'DELETE'),
    getCount: () => apiCall('/suppliers/count')
};

// ============== ALERT API ==============

const AlertAPI = {
    getAll: () => apiCall('/alerts'),
    getByType: (type) => apiCall(`/alerts/type/${type}`),
    getBySeverity: (severity) => apiCall(`/alerts/severity/${severity}`),
    resolve: (alertId, resolvedBy) => 
        apiCall(`/alerts/${alertId}/resolve?resolvedBy=${resolvedBy}`, 'POST'),
    getCount: () => apiCall('/alerts/count'),
    getCriticalCount: () => apiCall('/alerts/critical-count')
};

// ============== UTILITY FUNCTIONS ==============

/**
 * Format currency
 */
function formatCurrency(value) {
    return '₹' + value.toLocaleString('en-IN', { 
        minimumFractionDigits: 2,
        maximumFractionDigits: 2 
    });
}

/**
 * Format date
 */
function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString('en-IN', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

/**
 * Format datetime
 */
function formatDateTime(dateString) {
    return new Date(dateString).toLocaleDateString('en-IN', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

/**
 * Show toast notification
 */
function showToast(message, type = 'info', duration = 3000) {
    const toastHTML = `
        <div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header bg-${type}">
                <strong class="me-auto">${type.charAt(0).toUpperCase() + type.slice(1)}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
            </div>
            <div class="toast-body">
                ${message}
            </div>
        </div>
    `;
    
    const toastContainer = document.getElementById('toastContainer') || createToastContainer();
    toastContainer.insertAdjacentHTML('beforeend', toastHTML);
    
    const toastElement = toastContainer.lastElementChild;
    const toast = new bootstrap.Toast(toastElement, { delay: duration });
    toast.show();
    
    setTimeout(() => toastElement.remove(), duration);
}

/**
 * Create toast container if it doesn't exist
 */
function createToastContainer() {
    const container = document.createElement('div');
    container.id = 'toastContainer';
    container.className = 'position-fixed top-0 end-0 p-3';
    container.style.zIndex = '9999';
    document.body.appendChild(container);
    return container;
}

/**
 * Show loading spinner
 */
function showSpinner(elementId) {
    const element = document.getElementById(elementId);
    if (element) {
        element.innerHTML = `
            <div class="text-center">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        `;
    }
}

/**
 * Get status badge HTML
 */
function getStatusBadge(status) {
    const statusMap = {
        'ACTIVE': { class: 'badge-status status-active', text: 'Active' },
        'INACTIVE': { class: 'badge-status status-inactive', text: 'Inactive' },
        'PENDING': { class: 'badge-status status-pending', text: 'Pending' },
        'COMPLETED': { class: 'badge-status status-completed', text: 'Completed' },
        'CRITICAL': { class: 'badge-status status-critical', text: 'Critical' },
        'LOW_STOCK': { class: 'badge-status status-pending', text: 'Low Stock' },
        'OVERSTOCK': { class: 'badge-status status-inactive', text: 'Overstock' }
    };
    
    const statusInfo = statusMap[status] || { class: 'badge-status', text: status };
    return `<span class="badge ${statusInfo.class}">${statusInfo.text}</span>`;
}

/**
 * Create a table from data array
 */
function createTable(data, columns) {
    if (!data || data.length === 0) {
        return '<p class="text-muted">No data available</p>';
    }

    let table = '<table class="table table-hover mb-0"><thead><tr>';
    
    // Create headers
    columns.forEach(col => {
        table += `<th>${col.label}</th>`;
    });
    table += '</tr></thead><tbody>';

    // Create rows
    data.forEach(row => {
        table += '<tr>';
        columns.forEach(col => {
            let value = row[col.key];
            if (col.format) {
                value = col.format(value);
            }
            table += `<td>${value || '-'}</td>`;
        });
        table += '</tr>';
    });

    table += '</tbody></table>';
    return table;
}

/**
 * Confirm dialog
 */
function confirmAction(message) {
    return confirm(message);
}

/**
 * Debounce function
 */
function debounce(func, delay) {
    let timeoutId;
    return function(...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => func(...args), delay);
    };
}

/**
 * Update current date/time
 */
function updateCurrentDateTime() {
    const element = document.getElementById('currentDateTime');
    if (element) {
        const now = new Date();
        element.textContent = now.toLocaleDateString('en-IN', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }
}

// Update date/time on page load and every minute
document.addEventListener('DOMContentLoaded', () => {
    updateCurrentDateTime();
    setInterval(updateCurrentDateTime, 60000);
});
