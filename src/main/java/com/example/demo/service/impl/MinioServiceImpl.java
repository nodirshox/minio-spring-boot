package com.example.demo.service.impl;

import com.example.demo.service.MinioService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioServiceImpl implements MinioService {
    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.accessKey}")
    private String minioAccessKey;

    @Value("${minio.secretKey}")
    private String minioSecretKey;

    @Value("${minio.bucket}")
    private String minioBucket;

    @Override
    public String upload(MultipartFile file) {
        try {
            String extension = this.getFileExtension(file);
            long timeStamp = System.currentTimeMillis() / 1000L;
            String newFilename = timeStamp + extension;

            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioUrl)
                            .credentials(minioAccessKey, minioSecretKey)
                            .build();

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioBucket).object(newFilename).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
            return newFilename;
        } catch (MinioException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }
}
