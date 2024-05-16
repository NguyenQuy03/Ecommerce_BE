package com.ecommerce.springbootecommerce.api.common.uploadFile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadFileAPI {

    @PostMapping
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            if (file != null
                    && (!file.getContentType().equals("image/jpeg")
                            && !file.getContentType().equals("image/png"))) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("File content is empty");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
