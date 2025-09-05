package com.lifestyle.product.Service;


import com.lifestyle.product.Data.*;
import com.lifestyle.product.Repository.ProductRepo;
import com.lifestyle.product.utility.GeneralService;
import com.lifestyle.product.utility.Response;
import com.lifestyle.product.utility.ResponseCodeAndMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private GeneralService generalService;

    @Transactional
    public Response createProduct(ProductReq request) {
          ProductResp productResp = new ProductResp();
        try {
            productResp = productRepo.createProduct(request.getProductName(), request.getCategoryName(), request.getVendorName(),
                                                    request.getPrice(), request.getSubcategoryName());
            if (productResp != null) {
                return generalService.prepareSuccessResponse(productResp);
            } else {
                return generalService.prepareFailedResponse(
                        ResponseCodeAndMessage.NOT_CREATED.responseCode,
                        ResponseCodeAndMessage.NOT_CREATED.responseMessage
                );
            }
        } catch (Exception ex) {
            log.error("Unexpected error while Creating Product: {}", ex.getMessage(), ex);
            return generalService.prepareFailedResponse(
                    ResponseCodeAndMessage.ERROR_PROCESSING.responseCode,
                    "Unexpected error while Creating Product"
            );
        }
    }

//    @Transactional
//    public Response createImage(ImageUploadReq request) {
//        ProductResp productResp = new ProductResp();
//        try {
//            productResp = productRepo.createProduct(request., request.getCategoryName(), request.getVendorName(),
//                    request.getPrice(), request.getSubcategoryName());
//            if (productResp != null) {
//                return generalService.prepareSuccessResponse(productResp);
//            } else {
//                return generalService.prepareFailedResponse(
//                        ResponseCodeAndMessage.NOT_CREATED.responseCode,
//                        ResponseCodeAndMessage.NOT_CREATED.responseMessage
//                );
//            }
//        } catch (Exception ex) {
//            log.error("Unexpected error while Creating Product: {}", ex.getMessage(), ex);
//            return generalService.prepareFailedResponse(
//                    ResponseCodeAndMessage.ERROR_PROCESSING.responseCode,
//                    "Unexpected error while Creating Product"
//            );
//        }
//    }

    public Response getProducts(String categoryName, String subcategoryName, String vendorName) {
        try {
            List<ProductListResp> products = productRepo.getProducts(categoryName, subcategoryName, vendorName);

            if (products != null && !products.isEmpty()) {
                return generalService.prepareSuccessResponse(products);
            } else {
                return generalService.prepareFailedResponse(
                        ResponseCodeAndMessage.RECORD_NOT_FOUND.responseCode,
                        ResponseCodeAndMessage.RECORD_NOT_FOUND.responseMessage
                );
            }
        } catch (Exception ex) {
            log.error("Unexpected error while fetching products: {}", ex.getMessage(), ex);
            return generalService.prepareFailedResponse(
                    ResponseCodeAndMessage.ERROR_PROCESSING.responseCode,
                    "Unexpected error while fetching products"
            );
        }
    }

    @Transactional
    public List<ImageUploadResp> uploadImage(Long productId, List<MultipartFile> files, boolean isPrimary) {
        List<ImageUploadResp> responses = new ArrayList<>();
        log.info("Entering Upload for productId=" + productId + " with " + files.size() + " files");

        String uploadDir = "C:/lifestyle/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            log.info("Upload directory created: " + created);
        }
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);

            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destination = new File(uploadDir, uniqueFileName);
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                throw new RuntimeException("File upload failed for " + file.getOriginalFilename(), e);
            }
            String imageUrl = "/uploads/" + uniqueFileName;
            boolean makePrimary = (i == 0) && isPrimary;

            ImageUploadResp resp = productRepo.insertImage(productId, imageUrl, makePrimary);
            responses.add(resp);
        }

        return responses;
    }




}


