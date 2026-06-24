package com.warehouse.controller;

import com.warehouse.dao.SupplierDAO;
import com.warehouse.model.Supplier;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API Controller for Supplier Management
 */
@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin("*")
public class SupplierController {
    
    private final SupplierDAO supplierDAO = new SupplierDAO();

    /**
     * Get all suppliers
     */
    @GetMapping
    public Map<String, Object> getAllSuppliers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Supplier> suppliers = supplierDAO.getAllSuppliers();
            response.put("success", true);
            response.put("data", suppliers);
            response.put("count", suppliers.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get supplier by ID
     */
    @GetMapping("/{id}")
    public Map<String, Object> getSupplier(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Supplier supplier = supplierDAO.getSupplierById(id);
            if (supplier != null) {
                response.put("success", true);
                response.put("data", supplier);
            } else {
                response.put("success", false);
                response.put("error", "Supplier not found");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Add new supplier
     */
    @PostMapping
    public Map<String, Object> addSupplier(@RequestBody Supplier supplier) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = supplierDAO.addSupplier(supplier);
            response.put("success", success);
            if (success) {
                response.put("message", "Supplier added successfully");
            } else {
                response.put("error", "Failed to add supplier");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Update supplier
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateSupplier(@PathVariable int id, @RequestBody Supplier supplier) {
        Map<String, Object> response = new HashMap<>();
        try {
            supplier.setSupplierId(id);
            boolean success = supplierDAO.updateSupplier(supplier);
            response.put("success", success);
            if (success) {
                response.put("message", "Supplier updated successfully");
            } else {
                response.put("error", "Failed to update supplier");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Deactivate supplier
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deactivateSupplier(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = supplierDAO.deactivateSupplier(id);
            response.put("success", success);
            if (success) {
                response.put("message", "Supplier deactivated successfully");
            } else {
                response.put("error", "Failed to deactivate supplier");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get supplier count
     */
    @GetMapping("/count")
    public Map<String, Object> getSupplierCount() {
        Map<String, Object> response = new HashMap<>();
        try {
            int count = supplierDAO.getTotalSupplierCount();
            response.put("success", true);
            response.put("count", count);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
