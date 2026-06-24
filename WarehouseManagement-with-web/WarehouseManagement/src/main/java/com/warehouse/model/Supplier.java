package com.warehouse.model;

import java.time.LocalDateTime;

/**
 * Supplier - Model class representing a supplier
 */
public class Supplier {
    private int supplierId;
    private String supplierName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String paymentTerms;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;

    // Constructors
    public Supplier() {}

    public Supplier(String supplierName, String contactPerson, String email, String phone) {
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return String.format("Supplier{id=%d, name='%s', contact='%s', email='%s', status='%s'}",
                supplierId, supplierName, contactPerson, email, status);
    }
}
