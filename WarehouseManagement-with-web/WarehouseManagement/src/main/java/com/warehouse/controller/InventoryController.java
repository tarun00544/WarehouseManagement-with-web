package com.warehouse.controller;

import com.warehouse.dao.StockDAO;
import com.warehouse.model.Stock;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API Controller for Stock/Inventory Management
 */
@RestController
@RequestMapping("/api/inventory")
@CrossOrigin("*")
public class InventoryController {
    
    private final StockDAO stockDAO = new StockDAO();

    /**
     * Get all stock
     */
    @GetMapping
    public Map<String, Object> getAllStock() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Stock> stocks = stockDAO.getAllStock();
            response.put("success", true);
            response.put("data", stocks);
            response.put("count", stocks.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get stock by product ID
     */
    @GetMapping("/product/{productId}")
    public Map<String, Object> getStockByProduct(@PathVariable int productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Stock stock = stockDAO.getStockByProductId(productId);
            if (stock != null) {
                response.put("success", true);
                response.put("data", stock);
            } else {
                response.put("success", false);
                response.put("error", "Stock not found");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get low stock items
     */
    @GetMapping("/low-stock")
    public Map<String, Object> getLowStockItems(@RequestParam(defaultValue = "100") int threshold) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Stock> stocks = stockDAO.getLowStockItems(threshold);
            response.put("success", true);
            response.put("data", stocks);
            response.put("count", stocks.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get overstock items
     */
    @GetMapping("/overstock")
    public Map<String, Object> getOverstockItems() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Stock> stocks = stockDAO.getOverstockItems();
            response.put("success", true);
            response.put("data", stocks);
            response.put("count", stocks.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Update stock quantity
     */
    @PostMapping("/update/{productId}")
    public Map<String, Object> updateStock(@PathVariable int productId, 
                                           @RequestParam int quantity,
                                           @RequestParam String type) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = stockDAO.updateStockQuantity(productId, quantity, type);
            response.put("success", success);
            if (success) {
                response.put("message", "Stock updated successfully");
            } else {
                response.put("error", "Failed to update stock");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get total inventory value
     */
    @GetMapping("/total-value")
    public Map<String, Object> getTotalInventoryValue() {
        Map<String, Object> response = new HashMap<>();
        try {
            int totalValue = stockDAO.getTotalInventoryValue();
            response.put("success", true);
            response.put("totalValue", totalValue);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get total inventory quantity
     */
    @GetMapping("/total-quantity")
    public Map<String, Object> getTotalInventoryQuantity() {
        Map<String, Object> response = new HashMap<>();
        try {
            int totalQuantity = stockDAO.getTotalInventoryQuantity();
            response.put("success", true);
            response.put("totalQuantity", totalQuantity);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
