package com.lifestyle.product.Data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductListReq {

    private String categoryName;
    private String subcategoryName;
    private String vendorName;
}
