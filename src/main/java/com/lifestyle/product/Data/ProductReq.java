package com.lifestyle.product.Data;

import java.math.BigDecimal;

public class ProductReq {

    private String productName;
    private String vendorName;
    private String categoryName;
    private String subcategoryName;
    private BigDecimal price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductReq{" +
                "productName='" + productName + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", subcategoryName='" + subcategoryName + '\'' +
                ", price=" + price +
                '}';
    }
}
