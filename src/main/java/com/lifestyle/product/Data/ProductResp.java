package com.lifestyle.product.Data;

import java.math.BigDecimal;

public class ProductResp {

    private Long productId;
    private String productName;
    private String vendorName;
    private String categoryName;
    private String subcategoryName;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

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


    @Override
    public String toString() {
        return "ProductResp{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", subcategoryName='" + subcategoryName + '\'' +
                '}';
    }
}
