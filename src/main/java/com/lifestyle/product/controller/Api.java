package com.lifestyle.product.controller;

import com.lifestyle.product.Data.ImageUploadReq;
import com.lifestyle.product.Data.ImageUploadResp;
import com.lifestyle.product.Data.ProductReq;
import com.lifestyle.product.Service.ProductService;
import com.lifestyle.product.utility.GeneralService;
import com.lifestyle.product.utility.Response;
import com.lifestyle.product.utility.ResponseCodeAndMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Products", description = "Endpoints for managing products")
public class Api {

    @Autowired
    private ProductService productService;

    @Autowired
    private GeneralService generalService;


    @PostMapping("/create")
    @Operation(summary = "Create a new product", description = "Adds a product with category, subcategory, and vendor")
    public ResponseEntity<Response> createProd(@RequestBody ProductReq productReq) {
        try {
            Response response = productService.createProduct(productReq);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            Response errorResp = generalService.prepareFailedResponse(
                    ResponseCodeAndMessage.ERROR_PROCESSING.responseCode,
                    ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResp);
        }
    }

    @GetMapping("/list")
    @Operation(summary = "List products", description = "Lists product using category, subcategory, and vendor")
    public ResponseEntity<Response> getProducts(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String subcategoryName,
            @RequestParam(required = false) String vendorName) {
        try {
            Response response = productService.getProducts(categoryName, subcategoryName, vendorName);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            Response errorResp = generalService.prepareFailedResponse(
                    ResponseCodeAndMessage.ERROR_PROCESSING.responseCode,
                    ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResp);
        }
    }

    @PostMapping("/{productId}/upload")
    @Operation(summary = "Upload product Image", description = "Upload product Image")
    public ResponseEntity<Response> uploadPhotos(
            @PathVariable Long productId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "isPrimary", defaultValue = "false") boolean isPrimary) {
        try {
            // Service returns list of uploaded photo DTOs
            List<ImageUploadResp> uploadedPhotos = productService.uploadImage(productId, files, isPrimary);

            Response response = new Response();
            response.setResponseCode(200);
            response.setResponseMessage("Uploaded " + uploadedPhotos.size() + " photos successfully");
            response.setData(uploadedPhotos);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Response errorResponse = new Response();
            errorResponse.setResponseCode(400);
            errorResponse.setResponseMessage("Upload failed: " + e.getMessage());
            errorResponse.setData(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }





}
