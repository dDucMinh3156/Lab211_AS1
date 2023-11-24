/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Comparator;

/**
 *
 * @author Ta Minh Duc
 */
public class Products implements Comparator<Products> {

    //-- Declare variables
    private String productID;
    private String productName;
    private float unitPrice;
    private int Quantity;
    private String Status;

    //-- Constructor default
    public Products() {
    }

    public Products(String productID, String productName, float unitPrice, int Quantity, String Status) {
        this.productID = productID;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.Quantity = Quantity;
        this.Status = Status;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getStatus() {
        return Status;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    //-- show data following the format
    @Override
    public String toString() {
        return String.format("| %-10s | %-26s | %-12.2f | %-8d | %-13s |", this.productID, this.productName, this.unitPrice, this.Quantity, this.Status);
    }

    //-- sort data following name product: ascending alphabet
    @Override
    public int compare(Products pr1, Products pr2) {
        return pr1.getProductName().compareTo(pr2.getProductName());
    }
}
