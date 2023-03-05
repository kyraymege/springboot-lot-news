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
import java.util.Date;

@RestController
@RequestMapping("/api/v1/upload")
public class ImageUploadController {

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        File resourcesDirectory = new File("src/main/resources/static/image");
        String PATH_DIRECTORY = resourcesDirectory.getAbsolutePath();
        String FILE_NAME = new Date().getTime()+"_"+file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get(PATH_DIRECTORY+ File.separator+ FILE_NAME), StandardCopyOption.REPLACE_EXISTING);

        return new ResponseEntity<>(FILE_NAME, HttpStatus.OK);
    }
}
