package com.lifestyle.product.Repository;

import com.lifestyle.product.Data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ProductRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ProductResp createProduct(String productName, String categoryName, String vendorName, BigDecimal price, String subcategoryName) {
        return jdbcTemplate.execute("{call dbo.Insert_Product(?, ?, ?, ?, ?)}",
                (CallableStatementCallback<ProductResp>) stmnt -> {
                    stmnt.setString(1, productName);
                    stmnt.setBigDecimal(2, price);
                    stmnt.setString(3, categoryName);
                    stmnt.setString(4, subcategoryName);
                    stmnt.setString(5, vendorName);

                    boolean hasResult = stmnt.execute();
                    if (hasResult) {
                        try (ResultSet rs = stmnt.getResultSet()) {
                            if (rs.next()) {
                                ProductResp resp = new ProductResp();
                                resp.setProductId(rs.getLong("productId"));
                                resp.setProductName(rs.getString("productName"));
                                resp.setVendorName(rs.getString("vendorName"));
                                resp.setCategoryName(rs.getString("categoryName"));
                                resp.setSubcategoryName(rs.getString("subcategoryName"));
                                return resp;
                            }
                        }
                    }
                    throw new RuntimeException("No product details returned from Database");
                }
        );
    }

    public ImageUploadResp insertImage(Long productId, String imageUrl, Boolean isPrimary) {
        log.info("productId" + productId);
        return jdbcTemplate.execute("{call dbo.Insert_Image(?, ?, ?)}",
                (CallableStatementCallback<ImageUploadResp>) stmnt -> {
                    stmnt.setLong(1, productId);
                    stmnt.setString(2, imageUrl);
                    stmnt.setBoolean(3, isPrimary);

                    boolean hasResult = stmnt.execute();
                    log.info("Database Result" + hasResult);
                    if (hasResult) {
                        try (ResultSet rs = stmnt.getResultSet()) {
                            if (rs.next()) {
                                ImageUploadResp resp = new ImageUploadResp();
                                resp.setProductId(rs.getLong("productId"));
                                resp.setImageUrl(rs.getString("imageUrl"));
                                resp.setPrimary(rs.getBoolean("isPrimary"));
                                return resp;
                            }
                        }
                    }
                    throw new RuntimeException("No product details returned from Database");
                }
        );
    }

    public List<ProductListResp> getProducts(String categoryName, String subcategoryName, String vendorName) {
        return jdbcTemplate.query(
                "{call dbo.Get_Products(?, ?, ?)}",
                new Object[]{categoryName, subcategoryName, vendorName},
                rs -> {
                    Map<Long, ProductListResp> productMap = new LinkedHashMap<>();

                    while (rs.next()) {
                        Long productId = rs.getLong("productId");

                        ProductListResp product = productMap.get(productId);
                        if (product == null) {
                            product = new ProductListResp();
                            product.setProductId(productId);
                            product.setProductName(rs.getString("productName"));
                            product.setPrice(rs.getBigDecimal("price"));
                            product.setCategoryName(rs.getString("categoryName"));
                            product.setSubcategoryName(rs.getString("subcategoryName"));
                            product.setVendorName(rs.getString("vendorName"));
                            productMap.put(productId, product);
                        }

                        String imageUrl = rs.getString("imageUrl");
                        if (imageUrl != null) {
                            ImageResp image = new ImageResp();
                            image.setImageUrl(imageUrl);
                            image.setIsPrimary(rs.getBoolean("isPrimary"));
                            product.getImages().add(image);
                        }
                    }

                    return new ArrayList<>(productMap.values());
                }
        );
    }

}


