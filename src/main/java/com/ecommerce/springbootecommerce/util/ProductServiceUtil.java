package com.ecommerce.springbootecommerce.util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;

@Component
public class ProductServiceUtil {

    @Autowired
    private Cloudinary cloudinary;
    
    public String updateProductImage(ProductDTO productDTO) {
        String imageUrl = "";
        try {
            imageUrl = cloudinary.uploader()
                    .upload(
                        productDTO.getImage(), 
                        Map.of(
                            "public_id", UUID.randomUUID().toString(),
                            "folder", "Product_Image"
                        )
                    )
                    .get("url")
                    .toString();
        } catch (IOException e) {
            return null;
        }
        return imageUrl;
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
                                    "public_id", UUID.randomUUID().toString(),
                                    "folder", "Product_Item_Image"
                                )
                            )
                            .get("url")
                            .toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ProductItemimageUrl;
            });
            uploadFutures.add(uploadFuture);
        }

        return uploadFutures;
    }
}

