package com.warehouse.controller;

import com.warehouse.dao.ProductDAO;
import com.warehouse.model.Product;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API Controller for Product Management
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {
    
    private final ProductDAO productDAO = new ProductDAO();

    /**
     * Get all products
     */
    @GetMapping
    public Map<String, Object> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Product> products = productDAO.getAllProducts();
            response.put("success", true);
            response.put("data", products);
            response.put("count", products.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    public Map<String, Object> getProduct(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Product product = productDAO.getProductById(id);
            if (product != null) {
                response.put("success", true);
                response.put("data", product);
            } else {
                response.put("success", false);
                response.put("error", "Product not found");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Search products
     */
    @GetMapping("/search")
    public Map<String, Object> searchProducts(@RequestParam String term) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Product> products = productDAO.searchProducts(term);
            response.put("success", true);
            response.put("data", products);
            response.put("count", products.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Add new product
     */
    @PostMapping
    public Map<String, Object> addProduct(@RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = productDAO.addProduct(product);
            response.put("success", success);
            if (success) {
                response.put("message", "Product added successfully");
            } else {
                response.put("error", "Failed to add product");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Update product
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();
        try {
            product.setProductId(id);
            boolean success = productDAO.updateProduct(product);
            response.put("success", success);
            if (success) {
                response.put("message", "Product updated successfully");
            } else {
                response.put("error", "Failed to update product");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Delete product
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = productDAO.deleteProduct(id);
            response.put("success", success);
            if (success) {
                response.put("message", "Product deleted successfully");
            } else {
                response.put("error", "Failed to delete product");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    /**
     * Get product count
     */
    @GetMapping("/count")
    public Map<String, Object> getProductCount() {
        Map<String, Object> response = new HashMap<>();
        try {
            int count = productDAO.getTotalProductCount();
            response.put("success", true);
            response.put("count", count);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
