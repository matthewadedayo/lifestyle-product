package com.lifestyle.product.Data;



public class ImageUploadReq {

    private Long productId;
    private String imageUrl;
    private boolean isPrimary;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    @Override
    public String toString() {
        return "ImageUploadReq{" +
                "productId=" + productId +
                ", imageUrl='" + imageUrl + '\'' +
                ", isPrimary=" + isPrimary +
                '}';
    }
}
