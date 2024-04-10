package com.thaikimhuynh.test.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String productId;
    private String productName;
    private float unitPrice;
    private String imgLink;

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }



    public Product(String productId, String productName, float unitPrice, String imgLink) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.imgLink = imgLink;
    }

    public Product() {
    }

    @Override
    public String toString() {
        String msg= productId + " " + productName + " " + unitPrice + "$";
        return msg;
    }
}
