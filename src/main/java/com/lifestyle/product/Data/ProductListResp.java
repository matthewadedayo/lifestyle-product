package com.lifestyle.product.Data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductListResp {

    private Long productId;
    private String productName;
    private BigDecimal price;
    private String categoryName;
    private String subcategoryName;
    private String vendorName;
    private List<ImageResp> images = new ArrayList<>();

    @Override
    public String toString() {
        return "ProductListResp{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", categoryName='" + categoryName + '\'' +
                ", subcategoryName='" + subcategoryName + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", images=" + images +
                '}';
    }
}
