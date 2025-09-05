package com.lifestyle.product.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "ProductImage")
    public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long photoId;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "productId", nullable = false)
        private Product product;
        private String photoUrl;
        private boolean isPrimary;
        @Column(name = "creationDate")
        private LocalDateTime creationDate;

        public Long getPhotoId() {
            return photoId;
        }

        public void setPhotoId(Long photoId) {
            this.photoId = photoId;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public boolean isPrimary() {
            return isPrimary;
        }

        public void setPrimary(boolean primary) {
            isPrimary = primary;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
        }
    }
