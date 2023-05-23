package com.charapadev.blendsshop.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * Service used to perform the function related to store files like image of products or PDFs.
 */

@Service
public class StorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    /**
     * Converts a Base64 string to InputStream.
     *
     * @param base64 The Base64 string.
     * @return The InputStream of bytes.
     */
    private ByteArrayInputStream base64ToStream(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new ByteArrayInputStream(decodedBytes);
    }

    /**
     * Upload a given Base64 string into AWS S3 as a file.
     *
     * @param base64 The Base64 string content.
     * @return The URL of uploaded file.
     */
    public String uploadFile(String base64) {
        try {
            ByteArrayInputStream fileStream = base64ToStream(base64);
            long fileSize = fileStream.available();
            String randomFilename = UUID.randomUUID() + ".jpeg";

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileSize);
            metadata.setContentType("image/jpeg");

            amazonS3.putObject(bucketName, randomFilename, fileStream, metadata);
            fileStream.close();

            return resolveFileURL(randomFilename);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Mount the URL to access a given file uploaded on AWS S3.
     *
     * @param filename The file's name.
     * @return The fully mounted URL.
     */
    private String resolveFileURL(String filename) {
        return String.format("%s/%s/%s", s3Url, bucketName, filename);
    }

}
