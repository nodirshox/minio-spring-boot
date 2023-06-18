package com.example.demo.controller;

import com.example.demo.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {
    private final MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String filename = minioService.upload(file);
            return new ResponseEntity<>(filename, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload the file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}