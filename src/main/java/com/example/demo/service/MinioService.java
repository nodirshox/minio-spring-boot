package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String upload(MultipartFile file);
}
