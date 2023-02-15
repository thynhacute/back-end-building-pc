package com.webapp.buildPC.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.webapp.buildPC.service.interf.UploadFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class UploadFileServiceImpl implements UploadFileService {


    private static final String FIREBASE_PROJECT_ID = "web-build-pc-362d8";
    private static final String FIREBASE_STORAGE_BUCKET = "web-build-pc-362d8.appspot.com";
    private static final String FIREBASE_KEY_FILE_LOCATION = "src/main/resources/FirebaseAdminSDK.json";

    static {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(FIREBASE_KEY_FILE_LOCATION)))
                    .setStorageBucket(FIREBASE_STORAGE_BUCKET)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("image", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(FIREBASE_KEY_FILE_LOCATION)))
                .build()
                .getService();
        Bucket bucket = storage.get(FIREBASE_STORAGE_BUCKET);

        String imageUrl = String.format("gs://%s/%s", FIREBASE_STORAGE_BUCKET, tempFile.getFileName());
        bucket.create(tempFile.getFileName().toString(), new FileInputStream(tempFile.toFile()), "image/jpeg");

        return imageUrl;
    }

}
