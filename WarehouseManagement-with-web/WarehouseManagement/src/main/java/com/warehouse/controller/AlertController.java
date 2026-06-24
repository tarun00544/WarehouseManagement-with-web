package com.warehouse.controller;

import com.warehouse.dao.InventoryAlertDAO;
import com.warehouse.model.InventoryAlert;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API Controller for Inventory Alerts
 */
@RestController
@RequestMapping("/api/alerts")
@CrossOrigin("*")
public class AlertController {
    
    private final InventoryAlertDAO alertDAO = new InventoryAlertDAO();

    /**
     * Get all active alerts
     */
    @GetMapping
    public Map<String, Object> getActiveAlerts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<InventoryAlert> alerts = alertDAO.getActiveAlerts();
            response.put("success", true);
            response.put("data", alerts);
            response.put("count", alerts.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get alerts by type
     */
    @GetMapping("/type/{type}")
    public Map<String, Object> getAlertsByType(@PathVariable String type) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<InventoryAlert> alerts = alertDAO.getAlertsByType(type);
            response.put("success", true);
            response.put("data", alerts);
            response.put("count", alerts.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get alerts by severity
     */
    @GetMapping("/severity/{severity}")
    public Map<String, Object> getAlertsBySeverity(@PathVariable String severity) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<InventoryAlert> alerts = alertDAO.getAlertsBySeverity(severity);
            response.put("success", true);
            response.put("data", alerts);
            response.put("count", alerts.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Resolve alert
     */
    @PostMapping("/{alertId}/resolve")
    public Map<String, Object> resolveAlert(@PathVariable int alertId,
                                            @RequestParam String resolvedBy) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = alertDAO.resolveAlert(alertId, resolvedBy);
            response.put("success", success);
            if (success) {
                response.put("message", "Alert resolved successfully");
            } else {
                response.put("error", "Failed to resolve alert");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get active alert count
     */
    @GetMapping("/count")
    public Map<String, Object> getActiveAlertCount() {
        Map<String, Object> response = new HashMap<>();
        try {
            int count = alertDAO.getActiveAlertCount();
            response.put("success", true);
            response.put("count", count);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get critical alert count
     */
    @GetMapping("/critical-count")
    public Map<String, Object> getCriticalAlertCount() {
        Map<String, Object> response = new HashMap<>();
        try {
            int count = alertDAO.getCriticalAlertCount();
            response.put("success", true);
            response.put("count", count);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
