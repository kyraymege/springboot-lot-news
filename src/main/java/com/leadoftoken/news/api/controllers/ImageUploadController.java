package com.leadoftoken.news.api.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/v1/upload")
public class ImageUploadController {

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        String PATH_DIRECTORY = new ClassPathResource("static/images/").getFile().getAbsolutePath();
        Files.copy(file.getInputStream(), Paths.get(PATH_DIRECTORY+ File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

        return new ResponseEntity<>(file.getOriginalFilename(), HttpStatus.OK);
    }
}
