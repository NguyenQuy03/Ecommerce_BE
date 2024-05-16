package com.ecommerce.springbootecommerce.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.dto.ProductItemDTO;

@Component
public class ProductServiceUtil {

    private final String PRODUCT_IMAGE_FOLDER = "Product_Image";
    private final String PRODUCT_ITEM_IMAGE_FOLDER = "Product_Item_Image";

    @Autowired
    private Cloudinary cloudinary;

    public List<String> updateProductImages(ProductDTO productDTO) {
        List<String> imageUrls = new ArrayList<>();

        for (String image : productDTO.getProductImages()) {
            String imageUrl = "";
            try {
                imageUrl = cloudinary.uploader()
                        .upload(
                                image,
                                Map.of(
                                        "public_id", cloudinary.randomPublicId(),
                                        "folder", PRODUCT_IMAGE_FOLDER))
                        .get("url")
                        .toString();
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error Upload product image");
            }
        }

        return imageUrls;
    }

    public List<Future<String>> productItemImages(List<ProductItemDTO> productItems, ExecutorService executorService) {
        List<Future<String>> uploadFutures = new LinkedList<>();

        for (int i = 0; i < productItems.size(); i++) {
            int index = i;
            Future<String> uploadFuture = executorService.submit(() -> {
                String ProductItemimageUrl = "";
                try {
                    ProductItemimageUrl = cloudinary.uploader()
                            .upload(
                                    productItems.get(index).getImage(),
                                    Map.of(
                                            "public_id", cloudinary.randomPublicId(),
                                            "folder", PRODUCT_ITEM_IMAGE_FOLDER))
                            .get("url")
                            .toString();
                } catch (IOException e) {
                    throw new RuntimeException("Error Upload product item image");
                }
                return ProductItemimageUrl;
            });
            uploadFutures.add(uploadFuture);
        }

        return uploadFutures;
    }
}
